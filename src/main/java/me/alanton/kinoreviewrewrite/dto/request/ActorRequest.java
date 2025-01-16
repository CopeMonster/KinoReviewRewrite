package me.alanton.kinoreviewrewrite.dto.request;

import lombok.Builder;

import java.util.Set;

@Builder
public record ActorRequest(
        String username,
        String email,
        String password,
        Set<RoleRequest> roles
) {
}
