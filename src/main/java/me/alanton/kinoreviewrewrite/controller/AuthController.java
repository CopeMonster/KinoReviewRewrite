package me.alanton.kinoreviewrewrite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.request.SignInRequest;
import me.alanton.kinoreviewrewrite.dto.request.SignUpRequest;
import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import me.alanton.kinoreviewrewrite.dto.response.AuthResponse;
import me.alanton.kinoreviewrewrite.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication controller", description = "Authentication operations for signing in and signing up users")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Sign in",
            description = "Allows a user to sign in by providing their credentials (username and password).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign in request containing username and password.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignInRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully signed in",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials provided"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<AuthResponse> signIn(
            @RequestBody @Valid SignInRequest signInRequest
    ) {
        AuthResponse authResponse = authService.signIn(signInRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/sign-up")
    @Operation(
            summary = "Sign up",
            description = "Allows a new user to sign up by providing the necessary details like username, password, etc.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Sign up request containing user details for registration.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a new user",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<ActorResponse> signUp(
            @RequestBody @Valid SignUpRequest signUpRequest
    ) {
        ActorResponse actorResponse = authService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(actorResponse);
    }
}

