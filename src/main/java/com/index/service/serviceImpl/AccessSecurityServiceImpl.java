package com.index.service.serviceImpl;

import com.index.exception.HasNoAddAccessException;
import com.index.exception.SubjectNotFoundException;
import com.index.model.Role;
import com.index.model.User;
import com.index.repository.SubjectRepository;
import com.index.service.AccessSecurityService;
import com.index.service.AuthService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccessSecurityServiceImpl implements AccessSecurityService {

    AuthService authService;
    UserService userService;
    SubjectRepository subjectRepository;

    @Override
    public void checkHasAddAccess() {
        User user = authService.getCurrentUser();
        if (!user.getRole().equals(Role.ROLE_TEACHER)) {
            throw new HasNoAddAccessException();
        }
    }

    @Override
    public void checkAddGradeToStudent(long userId) {
        User user = userService.findById(userId);
        if (!user.getRole().equals(Role.ROLE_STUDENT)) {
            throw new HasNoAddAccessException();
        }
    }

    @Override
    public void checkIfSubjectExists(long subjectId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new SubjectNotFoundException(subjectId));
    }
}
