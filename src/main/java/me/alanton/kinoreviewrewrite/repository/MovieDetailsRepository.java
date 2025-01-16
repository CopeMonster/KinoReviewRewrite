package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.MovieDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDetailsRepository extends JpaRepository<MovieDetails, Long> {
}
