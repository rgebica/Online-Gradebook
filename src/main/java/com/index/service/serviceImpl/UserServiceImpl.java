package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exception.UserNotFoundException;
import com.index.dto.NotificationEmail;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Class;
import com.index.model.Grade;
import com.index.model.User;
import com.index.repository.ClassRepository;
import com.index.repository.UserRepository;
import com.index.model.EmailCfg;
import com.index.service.DateService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthServiceImpl authService;
    MailService mailService;
    ClassRepository classRepository;
    EmailCfg emailCfg;

    @Override
    public void createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        String password = generateUserPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setRole(createUserDto.getRole());
        user.setChildrenIds(createUserDto.getChildrenIds());
        user.setClassId(createUserDto.getClassId());

        userRepository.save(user);

        String token = authService.generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Dane użytkownika",
                user.getEmail(), "Konto zostało stworzone, sprawdź swoje dane : " + "\n\n" +
                "Imię: " + user.getFirstName() + "\n" +
                "Nazwisko: " + user.getLastName() + "\n" +
                "Rola: " + user.getRole() +
                "\n\n" + "Nazwa użytkownika: " + user.getUsername() + "\n" +
                "Wygenerowane hasło: " + password + "\n\n" +
                "Powinieneś zmienić hasło po pierwszym logowaniu" + "\n\n" +
                "Jeżeli dane się zgadzają, aktywuj konto otwierając link :" + "\n" + ("http://localhost:8080/api/auth/account-verification/" + token) + "\n\n" +
                "https://gradebook-server-online.herokuapp.com/api/auth/account-verification/" + token + "\n\n" +
                "W przypadku błędnych danych skontaktuj się z administratorem: "));

    }

    private String generateUserPassword() {
        String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chars = "abcdefghijklmnopqrstuwxyz";
        String nums = "0123456789";
        String symbols = "!@#$%^&*";
        String parentCode = charsCaps + chars + nums + symbols;
        Random rand = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randIndex = rand.nextInt(parentCode.length());
            password.append(parentCode.charAt(randIndex));
        }
        return password.toString();
    }

    @Override
    @Transactional
    public void deleteUsersByIds(String userIds) {
        String[] splitedIds = userIds.split(",");
        List<Long> parsedUserIds = Arrays.stream(splitedIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        userRepository.deleteUsersByIds(parsedUserIds);
    }

    @Override
    public UserDto getById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .dto();
    }

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<UserDto> findUsersByClass(long classId) {
        return userRepository.findAllByClassId(classId).stream()
                .map(User::dto)
                .collect(Collectors.toList());
    }

    public Class findClass (long userId) {
        return userRepository.findByClassId(userId);
    }

    @Override
    public StudentsDto getAllStudents() {
        List<UserDto> findAllStudents = userRepository.findAllStudents().stream()
                .map(User::dto)
                .collect(Collectors.toList());

        return StudentsDto.builder()
                .students(findAllStudents)
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(User::dto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllTeachers() {
        return userRepository.findAllTeachers().stream()
                .filter(user -> user.getRole().equals("ROLE_TEACHER"))
                .map(User::dto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllParents() {
        return userRepository.findAllParents().stream()
                .map(User::dto)
                .collect(Collectors.toList());
    }

    @Override
    public void editPassword(EditPasswordDto editPasswordDto, long userId) {
        User user = findById(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(editPasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(editPasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new SpringGradebookException("Bad old password");
        }
    }

    @Override
    public void editBasicInfo(UserEditInfoDto userEditInfoDto, long userId) {
        User user = findById(userId);
        user.setFirstName(userEditInfoDto.getFirstName());
        user.setFirstName(userEditInfoDto.getLastName());
        user.setEmail(userEditInfoDto.getEmail());
        user.setUsername(userEditInfoDto.getUsername());
        userRepository.save(user);
    }

    @Override
    public void resetPassword(long userId) {
        User user = findById(userId);
        String newGeneratedPassword = generateUserPassword();
        String encodedPassword = passwordEncoder.encode(newGeneratedPassword);
        userRepository.setNewPassword(encodedPassword, userId);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("gradebook@gradebook.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password reset");

        mailMessage.setText("Twoje hasło zostało zmienione, nowe hasło : " + newGeneratedPassword);
        // Send mail
        mailSender.send(mailMessage);
    }
}
