package me.alanton.kinoreviewrewrite.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @NotBlank(message = "Title cannot be empty or null")
        @Size(max = 255, message = "Title cannot exceed 255 characters")
        String title,

        @NotBlank(message = "Pros cannot be empty or null")
        @Size(max = 500, message = "Pros cannot exceed 500 characters")
        String pros,

        @NotBlank(message = "Cons cannot be empty or null")
        @Size(max = 500, message = "Cons cannot exceed 500 characters")
        String cons,

        @NotBlank(message = "Description cannot be empty or null")
        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @NotNull(message = "Rating cannot be null")
        @PositiveOrZero(message = "Rating must be a positive number or zero")
        Float rating
) { }