package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.Movie;
import me.alanton.kinoreviewrewrite.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findByMovie(Movie movie, Pageable pageable);
}
