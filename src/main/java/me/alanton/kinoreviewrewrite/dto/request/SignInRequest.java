package me.alanton.kinoreviewrewrite.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "Request to sign in user")
public record SignInRequest(
        @Schema(description = "User's name", example = "Alan")
        @NotBlank(message = "Username must not be blank")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain alphanumeric characters and underscores")
        String username,

        @Schema(description = "User's password", example = "strong_password")
        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 255, message = "Password must be at least 8 characters long")
        String password
) { }
