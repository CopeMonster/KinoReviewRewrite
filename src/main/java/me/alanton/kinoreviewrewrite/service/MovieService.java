package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.request.MovieRequest;
import me.alanton.kinoreviewrewrite.dto.response.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    MovieResponse getMovieById(Long id);
    Page<MovieResponse> getAllMovies(Pageable pageable);
    MovieResponse saveMovie(MovieRequest requestDTO);
    MovieResponse updateMovie(Long id, MovieRequest requestDTO);
    void deleteMovie(Long id);
    void updateAverageRating();
}
