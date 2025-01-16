package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.request.GenreRequest;
import me.alanton.kinoreviewrewrite.dto.response.GenreResponse;

import java.util.List;

public interface GenreService {
    GenreResponse getGenreById(Long id);
    List<GenreResponse> getAllGenres();
    GenreResponse saveGenre(GenreRequest createRequest);
    GenreResponse updateGenre(Long id, GenreRequest updateRequest);
    void deleteGenre(Long id);
}
