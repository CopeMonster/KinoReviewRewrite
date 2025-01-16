package me.alanton.kinoreviewrewrite.mapper;

import me.alanton.kinoreviewrewrite.dto.response.MovieResponse;
import me.alanton.kinoreviewrewrite.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {
    @Mapping(target = "description", source = "movie.movieDetails.description")
    @Mapping(target = "yearOfRelease", source = "movie.movieDetails.yearOfRelease")
    @Mapping(target = "country", source = "movie.movieDetails.country")
    @Mapping(target = "director", source = "movie.movieDetails.director")
    @Mapping(target = "duration", source = "movie.movieDetails.duration")
    @Mapping(target = "rating", source = "movie.movieDetails.rating")
    MovieResponse toMovieResponse(Movie movie);

    List<MovieResponse> toMovieResponseList(List<Movie> users);
}
