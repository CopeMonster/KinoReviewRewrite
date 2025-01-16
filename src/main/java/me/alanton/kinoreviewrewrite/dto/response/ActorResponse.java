package me.alanton.kinoreviewrewrite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@Schema(description = "Response with actor details")
public record ActorResponse(

        @Schema(description = "Unique identifier of the actor", example = "b03c4b76-1f8d-4781-81a2-3545cd940e2b", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID id,

        @Schema(description = "The actor's username", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
        String username,

        @Schema(description = "The actor's email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @Schema(description = "Whether the actor's email is verified or not", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean verified,

        @Schema(description = "The roles assigned to the actor", requiredMode = Schema.RequiredMode.REQUIRED)
        Set<RoleResponse> roles
) { }