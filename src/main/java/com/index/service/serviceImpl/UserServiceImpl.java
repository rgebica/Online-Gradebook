package com.index.service.serviceImpl;

import com.index.dto.CreateUserDto;
import com.index.dto.UserDto;
import com.index.exception.UserNotFoundException;
import com.index.model.NotificationEmail;
import com.index.model.User;
import com.index.repository.UserRepository;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthServiceImpl authService;
    MailService mailService;

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
                "If your personal information are right open link and active you account:" + "\n" + ("http://localhost:8080/api/auth/account-Verification/" + token) + "\n\n" +
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
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userRepository::delete);
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

}
