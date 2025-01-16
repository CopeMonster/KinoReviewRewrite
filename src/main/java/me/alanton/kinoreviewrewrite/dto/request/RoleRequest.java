package me.alanton.kinoreviewrewrite.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RoleRequest(
        @NotEmpty(message = "Role name must not be empty")
        String name
) { }
