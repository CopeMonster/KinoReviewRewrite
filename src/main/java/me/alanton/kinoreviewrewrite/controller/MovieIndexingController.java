package me.alanton.kinoreviewrewrite.controller;

import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.service.MovieIndexingService;
import me.alanton.kinoreviewrewrite.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/movies")
public class MovieIndexingController {

    private final MovieIndexingService movieIndexingService;

    @GetMapping("/reindex")
    public ResponseEntity<String> reindex() {
        movieIndexingService.reindexAllMovies();

        return ResponseEntity.ok("ReIndexed");
    }
}
