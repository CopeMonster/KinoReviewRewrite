package me.alanton.kinoreviewrewrite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response with access token")
public record AuthResponse(
        @Schema(description = "Access token", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJUb25Ub255Iiwicm9sZXMiOlt7ImlkIjoxMDIsIm5hbWUiOiJ...")
        String accessToken,

        @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJUb25Ub255Iiwicm9sZXMiOlt7ImlkIjoxMDIsIm5hbWUiOiJ...")
        String refreshToken
) { }
