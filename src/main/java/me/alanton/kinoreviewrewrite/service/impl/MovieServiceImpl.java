package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.document.MovieDocument;
import me.alanton.kinoreviewrewrite.dto.request.GenreRequest;
import me.alanton.kinoreviewrewrite.dto.request.MovieRequest;
import me.alanton.kinoreviewrewrite.dto.response.MovieResponse;
import me.alanton.kinoreviewrewrite.entity.Movie;
import me.alanton.kinoreviewrewrite.entity.MovieDetails;
import me.alanton.kinoreviewrewrite.entity.Review;
import me.alanton.kinoreviewrewrite.exception.BusinessException;
import me.alanton.kinoreviewrewrite.exception.BusinessExceptionReason;
import me.alanton.kinoreviewrewrite.mapper.MovieMapper;
import me.alanton.kinoreviewrewrite.repository.GenreRepository;
import me.alanton.kinoreviewrewrite.repository.MovieDocumentRepository;
import me.alanton.kinoreviewrewrite.repository.MovieRepository;
import me.alanton.kinoreviewrewrite.service.MovieService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreRepository genreRepository;
    private final MovieDocumentRepository movieDocumentRepository;

    @Override
    @Transactional
    @Cacheable(value = "movies", key = "#id")
    public MovieResponse getMovieById(Long id) {
        log.info("Fetching movie with id: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.MOVIE_NOT_FOUND);
                });
        log.debug("Fetched movie: {}", movie);

        return movieMapper.toMovieResponse(movie);
    }

    @Override
    @Transactional
    public Page<MovieResponse> getAllMovies(Pageable pageable) {
        log.info("Fetching all movies with pagination: {}", pageable);
        Page<Movie> movies = movieRepository.findAll(pageable);
        log.debug("Fetched movies: {}", movies);

        return movies.map(movieMapper::toMovieResponse);
    }

    @Override
    @Transactional
    public MovieResponse saveMovie(MovieRequest createRequest) {
        Movie movie = Movie.builder()
                .name(createRequest.name())
                .genres(genreRepository.findAllByNameIn(createRequest.genres()
                        .stream()
                        .map(GenreRequest::name)
                        .toList()))
                .reviews(new ArrayList<>())
                .build();

        MovieDetails movieDetails = MovieDetails.builder()
                .description(createRequest.description())
                .yearOfRelease(createRequest.yearOfRelease())
                .country(createRequest.country())
                .director(createRequest.director())
                .duration(createRequest.duration())
                .rating(0.0F)
                .build();

        movie.setMovieDetails(movieDetails);
        movieDetails.setMovie(movie);

        movieRepository.save(movie);
        log.info("Saved movie with id: {}", movie.getId());
        log.debug("Saved movie: {}", movie);

        return movieMapper.toMovieResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest updateRequest) {
        log.debug("Fetching movie with id: {} to be updated", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.MOVIE_NOT_FOUND);
                });

        movie.setName(updateRequest.name());
        movie.setGenres(genreRepository.findAllByNameIn(updateRequest.genres()
                .stream()
                .map(GenreRequest::name)
                .toList()));

        MovieDetails movieDetails = movie.getMovieDetails();

        movieDetails.setDescription(updateRequest.description());
        movieDetails.setYearOfRelease(updateRequest.yearOfRelease());
        movieDetails.setCountry(updateRequest.country());
        movieDetails.setDirector(updateRequest.director());
        movieDetails.setDuration(updateRequest.duration());

        movieRepository.save(movie);
        log.info("Updated movie with id: {}", movie.getId());
        log.debug("Updated movie: {}", movie);

        return movieMapper.toMovieResponse(movie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        log.debug("Fetching movie with id: {} to be deleted", id);
        movieRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movie with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.MOVIE_NOT_FOUND);
                });
        log.info("Deleted movie with id: {}", id);

        movieRepository.deleteById(id);
    }

    @Override
    @Scheduled(fixedRate = 120000)
    public void updateAverageRating() {
        List<Movie> movies = movieRepository.findAll();

        for (Movie movie : movies) {
            List<Review> reviews = movie.getReviews();

            float averageRating = (float) reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);

            movie.getMovieDetails().setRating(averageRating);
            movieRepository.save(movie);
            log.info("Updated average rating for movie with id: {}", movie.getId());
        }
    }

    @Override
    @Transactional
    public Page<MovieResponse> searchMoviesViaElastic(String query, Pageable pageable) {
        Page<MovieDocument> searchResult = movieDocumentRepository.searchByQuery(query, pageable);

        Map<Long, Integer> idsMap = new HashMap<>();
        List<MovieDocument> movieDocuments = searchResult.getContent();
        for (int i = 0; i < movieDocuments.size(); i++) {
            idsMap.put(movieDocuments.get(i).getMovieId(), i);
        }

        Set<Long> ids = idsMap.keySet();

        List<MovieResponse> moviesFromDb = movieRepository.findAllById(ids)
                .stream()
                .map(movieMapper::toMovieResponse)
                .sorted(Comparator.comparingInt(movie -> idsMap.get(movie.id())))
                .collect(Collectors.toList());

        return new PageImpl<>(moviesFromDb, pageable, searchResult.getTotalElements());
    }
}
