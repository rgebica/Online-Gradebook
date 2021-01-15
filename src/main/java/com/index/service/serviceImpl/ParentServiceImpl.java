package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exception.HasNoAddAccessException;
import com.index.model.Behaviour;
import com.index.model.EmailCfg;
import com.index.model.Role;
import com.index.model.User;
import com.index.repository.BehaviourRepository;
import com.index.repository.UserRepository;
import com.index.service.AuthService;
import com.index.service.ParentService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;


@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParentServiceImpl implements ParentService {

    UserService userService;
    UserRepository userRepository;
    BehaviourRepository behaviourRepository;
    AuthService authService;
    EmailCfg emailCfg;

    @Override
    public ParentChildrenDto getParentPersonalInformation(long userId) {
        User user = userService.findById(userId);
        String[] childrenIds = user.getChildrenIds().split(",");
        List<Long> parsedChildrenIds = Arrays.stream(childrenIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<ChildrenDto> children = userRepository.findAllById(parsedChildrenIds).stream()
                .map(User::childrenDto)
                .collect(toCollection(ArrayList::new));

        return ParentChildrenDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .children(children)
                .build();
    }

    @Override
    public void createResponseToBehaviour(CreateBehaviourResponse createBehaviourResponse) {
        Behaviour behaviour = behaviourRepository.findById(createBehaviourResponse.getBehaviourId());
        UserDto user = userService.getById(behaviour.getAddedBy());
        String subject = createBehaviourResponse.getSubject();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(getFromEmail());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText("Ocena z zachowania: " + behaviour.getGrade() + "\n" +
                "Opis uwagi: " + behaviour.getDescription() + "\n" +
                "\n\n" + "Odpowiedź rodzica:" + "\n" +
                createBehaviourResponse.getResponseContent());

        mailSender.send(mailMessage);
    }

    public String getFromEmail() {
        return authService.getCurrentUser().getEmail();
    }

}
