package com.index.service.serviceImpl;

import com.index.dto.CreateUserDto;
import com.index.model.NotificationEmail;
import com.index.model.User;
import com.index.repository.UserRepository;
import com.index.service.AdminService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminServiceImpl implements AdminService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
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

        mailService.sendMail(new NotificationEmail("Please activate your Account",
                user.getEmail(), "Your account has been created," + "\n\n" + " login is: " + user.getUsername() + "\n\n" +
                "generated password is: " + password + "\n\n" +
                "you can change your password"));

        userRepository.save(user);
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
}
