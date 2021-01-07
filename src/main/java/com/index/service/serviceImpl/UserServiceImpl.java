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
import com.index.service.DateService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    Class _classNone = new Class(99, "None");

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

        userRepository.save(user);

        String token = authService.generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Personal Information",
                user.getEmail(), "Your account has been created. Check you personal information: " + "\n\n" +
                "First Name: " + user.getFirstName() + "\n" +
                "Last Name: " + user.getLastName() + "\n" +
                "Role: " + user.getRole() +
                "\n\n" + "Login: " + user.getUsername() + "\n" +
                "Generated password: " + password + "\n\n" +
                "You should change your password." + "\n\n" +
                "If your personal information are right open link and active you account:" + "\n" + ("http://localhost:8080/api/auth/account-verification/" + token) + "\n\n" +
                "https://gradebook-server-online.herokuapp.com/api/auth/account-verification/" + token + "\n\n" +
                "If something is bad with your data contact with admin: "));

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
                .peek(p -> {
                    if (p.getClassId() == null) {p.setClassId(_classNone);}
                })
                .map(User::dto)
                .collect(Collectors.toList());

        return StudentsDto.builder()
                .students(findAllStudents)
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .peek(p -> {
                    if (p.getClassId() == null) {p.setClassId(_classNone);}
                })
                .map(User::dto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllTeachers() {
        return userRepository.findAllTeachers().stream()
                .filter(user -> user.getRole().equals("ROLE_TEACHER"))
                .peek(p -> {
                    if (p.getClassId() == null) {p.setClassId(_classNone);}
                })
                .map(User::dto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllParents() {
        return userRepository.findAllParents().stream()
                .peek(p -> {
                    if (p.getClassId() == null) {p.setClassId(_classNone);}
                })
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
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        userRepository.save(user);
    }
}
