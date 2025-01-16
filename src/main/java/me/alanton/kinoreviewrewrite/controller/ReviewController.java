package me.alanton.kinoreviewrewrite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.request.ReviewRequest;
import me.alanton.kinoreviewrewrite.dto.response.ReviewResponse;
import me.alanton.kinoreviewrewrite.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/movies/{movieId}/reviews")
@Tag(name = "Review controller", description = "CRUD operations for movie reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    @Operation(
            summary = "Fetch review by ID",
            description = "Fetch a review by its unique identifier (ID) for a specific movie.",
            parameters = {
                    @Parameter(description = "Unique identifier of the movie", required = true, example = "1"),
                    @Parameter(description = "Unique identifier of the review", required = true, example = "1c9c7ee1-1c5b-4d65-9000-d95cfc53910c")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched review",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Review or Movie not found")
            }
    )
    public ResponseEntity<ReviewResponse> getReviewById(
            @PathVariable Long movieId,
            @PathVariable UUID reviewId
    ) {
        ReviewResponse reviewResponse = reviewService.getReviewById(reviewId);

        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping
    @Operation(
            summary = "Fetch reviews for a movie",
            description = "Fetch a list of reviews for a specific movie, with optional pagination support.",
            parameters = {
                    @Parameter(description = "Unique identifier of the movie", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched reviews",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "204", description = "No content found")
            }
    )
    public ResponseEntity<Page<ReviewResponse>> getMovieReviews(
            @PathVariable Long movieId,
            @Parameter(description = "Pageable information for pagination (page number, size, etc.)")
            Pageable pageable
    ) {
        Page<ReviewResponse> reviewResponses = reviewService.getMovieReviews(movieId, pageable);

        return ResponseEntity.ok(reviewResponses);
    }

    @PostMapping
    @Operation(
            summary = "Create a new review for a movie",
            description = "Create a new review for a specific movie by submitting a ReviewRequest body.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review creation request containing necessary details like title, pros, cons, description, rating, etc.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Review created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<ReviewResponse> saveReview(
            @PathVariable Long movieId,
            @RequestBody @Valid ReviewRequest reviewRequest
    ) {
        ReviewResponse reviewResponse = reviewService.saveReview(movieId, reviewRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
    }

    @PutMapping("/{reviewId}")
    @Operation(
            summary = "Update an existing review for a movie",
            description = "Update an existing review for a specific movie by providing its ID and a ReviewRequest body with updated data.",
            parameters = {
                    @Parameter(description = "Unique identifier of the movie", required = true, example = "1"),
                    @Parameter(description = "Unique identifier of the review to be updated", required = true, example = "1c9c7ee1-1c5b-4d65-9000-d95cfc53910c")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated review details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Review or Movie not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to update review")
            }
    )
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long movieId,
            @PathVariable UUID reviewId,
            @RequestBody @Valid ReviewRequest reviewRequest
    ) {
        ReviewResponse reviewResponse = reviewService.updateReview(reviewId, reviewRequest);

        return ResponseEntity.ok(reviewResponse);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(
            summary = "Delete a review for a movie",
            description = "Delete a review for a specific movie by its unique identifier (ID). This will permanently remove the review.",
            parameters = {
                    @Parameter(description = "Unique identifier of the movie", required = true, example = "1"),
                    @Parameter(description = "Unique identifier of the review to be deleted", required = true, example = "1c9c7ee1-1c5b-4d65-9000-d95cfc53910c")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Review or Movie not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to delete review")
            }
    )
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long movieId,
            @PathVariable UUID reviewId
    ) {
        reviewService.deleteReview(reviewId);

        return ResponseEntity.noContent().build();
    }
}
