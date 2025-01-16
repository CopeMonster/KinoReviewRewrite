package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.dto.request.GenreRequest;
import me.alanton.kinoreviewrewrite.dto.response.GenreResponse;
import me.alanton.kinoreviewrewrite.entity.Genre;
import me.alanton.kinoreviewrewrite.exception.BusinessException;
import me.alanton.kinoreviewrewrite.exception.BusinessExceptionReason;
import me.alanton.kinoreviewrewrite.mapper.GenreMapper;
import me.alanton.kinoreviewrewrite.repository.GenreRepository;
import me.alanton.kinoreviewrewrite.service.GenreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreResponse getGenreById(Long id) {
        log.info("Fetching genre with id: {}", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Genre with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.GENRE_NOT_FOUND);
                });

        log.debug("Fetched genre: {}", genre);

        return genreMapper.toGenreResponse(genre);
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        log.info("Fetching all genres");
        List<Genre> genres = genreRepository.findAll();
        log.debug("Fetched genres: {}", genres);

        return genreMapper.toGenreResponseList(genres);
    }

    @Override
    @Transactional
    public GenreResponse saveGenre(GenreRequest createRequest) {
        Genre genre = Genre.builder()
                .name(createRequest.name())
                .build();

        genreRepository.save(genre);
        log.info("Saved genre with id: {}", genre.getId());
        log.debug("Saved genre: {}", genre);

        return genreMapper.toGenreResponse(genre);
    }

    @Override
    @Transactional
    public GenreResponse updateGenre(Long id, GenreRequest updateRequest) {
        log.debug("Fetching genre with id: {} to be updated", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Genre with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.GENRE_NOT_FOUND);
                });

        genre.setName(updateRequest.name());

        genreRepository.save(genre);
        log.info("Updated genre with id: {}", genre.getId());
        log.debug("Updated genre: {}", genre);

        return genreMapper.toGenreResponse(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Long id) {
        log.debug("Fetching genre with id: {} to be deleted", id);
        genreRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Genre with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.GENRE_NOT_FOUND);
                });
        log.info("Deleted genre with id: {}", id);

        genreRepository.deleteById(id);
    }
}
