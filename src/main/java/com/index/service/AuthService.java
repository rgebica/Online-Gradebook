package com.index.service;

import com.index.dto.AuthenticationResponse;
import com.index.dto.LoginRequest;
import com.index.dto.RefreshTokenRequest;
import com.index.model.User;

public interface AuthService {
    User getCurrentUser();

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
