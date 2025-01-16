package me.alanton.kinoreviewrewrite.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Builder
public record MovieRequest(
        @NotBlank(message = "Name must be not blank")
        String name,

        @NotNull(message = "Genres must be not null")
        @Size(min = 1, message = "There must be at least one genre")
        List<GenreRequest> genres,

        String description,

        @Pattern(regexp = "\\d{4}", message = "Year of release must be a valid 4-digit year")
        String yearOfRelease,

        @NotBlank(message = "Country must be not blank")
        String country,

        @NotBlank(message = "Director must be not blank")
        String director,

        @NotNull
        Integer duration
) { }
