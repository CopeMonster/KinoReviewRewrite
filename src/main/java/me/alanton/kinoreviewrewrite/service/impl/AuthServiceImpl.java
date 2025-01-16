package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.dto.request.ActorRequest;
import me.alanton.kinoreviewrewrite.dto.request.RoleRequest;
import me.alanton.kinoreviewrewrite.dto.request.SignInRequest;
import me.alanton.kinoreviewrewrite.dto.request.SignUpRequest;
import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import me.alanton.kinoreviewrewrite.dto.response.AuthResponse;
import me.alanton.kinoreviewrewrite.service.ActorService;
import me.alanton.kinoreviewrewrite.service.AuthService;
import me.alanton.kinoreviewrewrite.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ActorService actorService;
    private final JwtService jwtService;

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        log.info("Signing in user with username: {}", signInRequest.username());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.username(),
                signInRequest.password()
        ));

        UserDetails user = actorService.loadUserByUsername(signInRequest.username());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshAccessToken(user);

        log.info("User signed in successfully, access token generated for username: {}", signInRequest.username());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public ActorResponse signUp(SignUpRequest signUpRequest) {
        log.info("Signing up new user: {}", signUpRequest.username());
        ActorRequest actorRequest = ActorRequest.builder()
                .username(signUpRequest.username())
                .email(signUpRequest.email())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .roles(Set.of(new RoleRequest("USER")))
                .build();

        ActorResponse actorResponse = actorService.saveActor(actorRequest);
        log.info("User signed up successfully with username: {}", signUpRequest.username());

        return actorResponse;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        log.info("Refreshing token for refreshToken: {}", refreshToken);
        return null;
    }

    @Override
    public void logout(String refreshToken) {
        log.info("Logging out user with refreshToken: {}", refreshToken);
    }
}
