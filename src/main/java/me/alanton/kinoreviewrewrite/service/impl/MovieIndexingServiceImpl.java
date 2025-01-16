package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.document.MovieDocument;
import me.alanton.kinoreviewrewrite.entity.Genre;
import me.alanton.kinoreviewrewrite.entity.Movie;
import me.alanton.kinoreviewrewrite.repository.MovieDocumentRepository;
import me.alanton.kinoreviewrewrite.repository.MovieRepository;
import me.alanton.kinoreviewrewrite.service.MovieIndexingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

@Service
public class MovieIndexingServiceImpl implements MovieIndexingService {
    private final MovieRepository movieRepository;
    private final MovieDocumentRepository movieDocumentRepository;

    @Override
    @Transactional
    public void reindexAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        movieDocumentRepository.saveAll(
            movies.stream().map(movie -> MovieDocument.builder()
                            .movieId(movie.getId())
                            .name(movie.getName())
                            .genres(movie.getGenres().stream().map(Genre::getName).toList())
                            .description(movie.getMovieDetails().getDescription())
                            .yearOfRelease(movie.getMovieDetails().getYearOfRelease())
                            .country(movie.getMovieDetails().getCountry())
                            .director(movie.getMovieDetails().getDirector())
                            .build())
                    .toList()
        );
    }
}
