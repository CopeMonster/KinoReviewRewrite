package me.alanton.kinoreviewrewrite.dto.response;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Builder
@Schema(description = "Response with genre details")
public record GenreResponse(

        @Schema(description = "Name of the genre", example = "Horror", requiredMode = Schema.RequiredMode.REQUIRED)
        String name
) implements Serializable { }
