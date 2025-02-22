package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllByNameIn(List<String> names);
}
