package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.model.User;
import com.index.repository.UserRepository;
import com.index.service.ParentService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toCollection;


@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParentServiceImpl implements ParentService {

    UserService userService;
    UserRepository userRepository;

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
}
