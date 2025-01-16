package me.alanton.kinoreviewrewrite.mapper;

import me.alanton.kinoreviewrewrite.dto.response.GenreResponse;
import me.alanton.kinoreviewrewrite.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {
    GenreResponse toGenreResponse(Genre genre);
    List<GenreResponse> toGenreResponseList(List<Genre> genres);
}
