package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
