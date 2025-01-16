package me.alanton.kinoreviewrewrite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.request.MovieRequest;
import me.alanton.kinoreviewrewrite.dto.response.MovieResponse;
import me.alanton.kinoreviewrewrite.entity.Actor;
import me.alanton.kinoreviewrewrite.entity.Movie;
import me.alanton.kinoreviewrewrite.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movie controller", description = "CRUD operations for movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<Page<MovieResponse>> searchMovies(
            @RequestParam("query") String query,
            Pageable pageable
    ) {
        Page<MovieResponse> movieResponses = movieService.searchMoviesViaElastic(query, pageable);

        return ResponseEntity.ok(movieResponses);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Fetch movie by ID",
            description = "Fetch a movie by its unique identifier (ID).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched movie",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    public ResponseEntity<MovieResponse> getMovieById(
            @PathVariable
            @Parameter(description = "Unique identifier of movie", required = true, example = "1")
            Long id
    ) {
        MovieResponse movieResponse = movieService.getMovieById(id);

        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping
    @Operation(
            summary = "Fetch all movies",
            description = "Fetch a list of all movies, with optional pagination support.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched movies",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "204", description = "No content found")
            }
    )
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @Parameter(description = "Pageable information for pagination (page number, size, etc.)")
            Pageable pageable
    ) {
        Page<MovieResponse> movieResponses = movieService.getAllMovies(pageable);

        return ResponseEntity.ok(movieResponses);
    }

    @PostMapping
    @Operation(
            summary = "Create a new movie",
            description = "Create a new movie by submitting a MovieCreateRequest body. A valid movie creation request is required.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Movie creation request containing necessary details like name, genre, etc.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Movie created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieResponse> saveMovie(
            @RequestBody
            @Valid MovieRequest createRequest
    ) {
        MovieResponse movieResponse = movieService.saveMovie(createRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieResponse);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing movie",
            description = "Update an existing movie by providing its ID and a MovieCreateRequest body with updated data.",
            parameters = {
                    @Parameter(description = "ID of the movie to be updated", required = true, example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated movie details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable
            @Parameter(description = "Unique identifier of movie", required = true, example = "1")

            Long id,
            @RequestBody @Valid MovieRequest updateRequest

    ) {
        MovieResponse movieResponse = movieService.updateMovie(id, updateRequest);

        return ResponseEntity.ok(movieResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a movie",
            description = "Delete a movie by its unique identifier (ID). This will permanently remove the movie.",
            parameters = {
                    @Parameter(description = "ID of the movie to be deleted", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Movie not found")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteMovie(
            @PathVariable
            @Parameter(description = "Unique identifier of movie", required = true, example = "1")
            Long id
    ) {
        movieService.deleteMovie(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(String.format("Movie with id %d was deleted", id));
    }
}
