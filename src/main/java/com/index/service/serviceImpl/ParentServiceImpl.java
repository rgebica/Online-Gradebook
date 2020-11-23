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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParentServiceImpl implements ParentService {

    UserService userService;
    UserRepository userRepository;

    @Override
    public ParentChildrenDto getParentPersonalInformation(long userId) {
        User user = userService.findById(userId);
        List<Long> childrenIds = user.getChildrenIds();
        List<UserDto> children = userRepository.findAllByChildrenIdsIn(childrenIds).stream()
                .map(User::dto)
                .collect(Collectors.toList());

        return ParentChildrenDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .children(children)
                .build();
    }

    public List<UserDto> findChildrenByUserId(List<Long> childrenIds) {
        return userRepository.findAllByChildrenIdsIn(childrenIds).stream()
                .map(User::dto)
                .collect(Collectors.toList());
    }
}
