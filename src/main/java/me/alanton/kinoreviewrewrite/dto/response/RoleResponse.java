package me.alanton.kinoreviewrewrite.dto.response;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Response with role details")
public record RoleResponse(

        @Schema(description = "Name of the role", example = "ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
        String name
) { }
