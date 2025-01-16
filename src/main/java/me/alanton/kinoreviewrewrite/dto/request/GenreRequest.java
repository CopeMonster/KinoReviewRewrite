package me.alanton.kinoreviewrewrite.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record GenreRequest(
        @NotEmpty(message = "Genre name must not be empty")
        String name
) { }
