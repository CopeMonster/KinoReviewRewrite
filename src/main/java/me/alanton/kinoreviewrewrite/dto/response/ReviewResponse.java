package me.alanton.kinoreviewrewrite.dto.response;

import lombok.*;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Builder
@Schema(description = "Response with review details")
public record ReviewResponse(

        @Schema(description = "Unique identifier of the review", example = "cfe63ab2-4cfc-44c1-8f34-27f60c473a6d", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID id,

        @Schema(description = "Title of the review", example = "Great Movie!", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,

        @Schema(description = "Pros of the movie mentioned in the review", example = "Excellent plot and acting.", requiredMode = Schema.RequiredMode.REQUIRED)
        String pros,

        @Schema(description = "Cons of the movie mentioned in the review", example = "Could have been scarier.", requiredMode = Schema.RequiredMode.REQUIRED)
        String cons,

        @Schema(description = "Full description of the review", example = "The movie kept me at the edge of my seat. The acting was superb, but I expected a bit more suspense.", requiredMode = Schema.RequiredMode.REQUIRED)
        String description,

        @Schema(description = "Rating given in the review (scale 1-10)", example = "8.0", requiredMode = Schema.RequiredMode.REQUIRED)
        float rating,

        ActorResponse actor,

        MovieResponse movie
) { }
