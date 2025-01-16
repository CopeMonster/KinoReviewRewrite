package me.alanton.kinoreviewrewrite.dto.response;

import lombok.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Response with movie details")
public record MovieResponse(

        @Schema(description = "Unique identifier of the movie", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "Name of the movie", example = "The Conjuring", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "List of genres associated with the movie", requiredMode = Schema.RequiredMode.REQUIRED)
        List<GenreResponse> genres,

        @Schema(description = "Description of the movie", example = "Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse.", requiredMode = Schema.RequiredMode.REQUIRED)
        String description,

        @Schema(description = "Year the movie was released", example = "2013", requiredMode = Schema.RequiredMode.REQUIRED)
        String yearOfRelease,

        @Schema(description = "Country where the movie was produced", example = "United States", requiredMode = Schema.RequiredMode.REQUIRED)
        String country,

        @Schema(description = "Director of the movie", example = "James Wan", requiredMode = Schema.RequiredMode.REQUIRED)
        String director,

        @Schema(description = "Duration of the movie in minutes", example = "112", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer duration,

        @Schema(description = "Rating of the movie (scale 1-10)", example = "7.5", requiredMode = Schema.RequiredMode.REQUIRED)
        Float rating
) { }
