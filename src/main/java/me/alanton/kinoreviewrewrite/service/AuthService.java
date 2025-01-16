package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.request.SignInRequest;
import me.alanton.kinoreviewrewrite.dto.request.SignUpRequest;
import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import me.alanton.kinoreviewrewrite.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signIn(SignInRequest sIgnInRequest);
    ActorResponse signUp(SignUpRequest signUpRequest);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}
