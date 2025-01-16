package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
