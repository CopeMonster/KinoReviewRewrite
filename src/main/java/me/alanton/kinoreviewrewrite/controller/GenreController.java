package me.alanton.kinoreviewrewrite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.request.GenreRequest;
import me.alanton.kinoreviewrewrite.dto.response.GenreResponse;
import me.alanton.kinoreviewrewrite.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/genres")
@Tag(name = "Genre controller", description = "CRUD operations for genres")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Fetch genre by ID",
            description = "Fetch a genre by its unique identifier (ID).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched genre",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    public ResponseEntity<GenreResponse> getGenreById(
            @PathVariable
            @Parameter(description = "Unique identifier of genre", required = true, example = "1")
            Long id
    ) {
        GenreResponse genreResponse = genreService.getGenreById(id);

        return ResponseEntity.ok(genreResponse);
    }

    @GetMapping
    @Operation(
            summary = "Fetch all genres",
            description = "Fetch a list of all genres.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched genres",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "204", description = "No content found")
            }
    )
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        List<GenreResponse> genreResponses = genreService.getAllGenres();

        return ResponseEntity.ok(genreResponses);
    }

    @PostMapping
    @Operation(
            summary = "Create a new genre",
            description = "Create a new genre by submitting a GenreCreateRequest body. A valid genre creation request is required.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Genre creation request containing necessary details like name.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Genre created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GenreResponse> saveGenre(
            @RequestBody
            @Valid GenreRequest createRequest
    ) {
        GenreResponse genreResponse = genreService.saveGenre(createRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(genreResponse);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing genre",
            description = "Update an existing genre by providing its ID and a GenreCreateRequest body with updated data.",
            parameters = {
                    @Parameter(description = "ID of the genre to be updated", required = true, example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated genre details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Genre updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenreResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable
            @Parameter(description = "Unique identifier of genre", required = true, example = "1")
            Long id,
            @RequestBody @Valid GenreRequest updateRequest
    ) {
        GenreResponse genreResponse = genreService.updateGenre(id, updateRequest);

        return ResponseEntity.ok(genreResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a genre",
            description = "Delete a genre by its unique identifier (ID). This will permanently remove the genre.",
            parameters = {
                    @Parameter(description = "ID of the genre to be deleted", required = true, example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Genre deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Genre not found")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteGenre(
            @PathVariable
            @Parameter(description = "Unique identifier of genre", required = true, example = "1")
            Long id
    ) {
        genreService.deleteGenre(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(String.format("Genre with id %d was deleted", id));
    }
}

