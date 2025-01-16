package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.request.ReviewRequest;
import me.alanton.kinoreviewrewrite.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewService {
    ReviewResponse getReviewById(UUID id);
    Page<ReviewResponse> getMovieReviews(Long movieId, Pageable pageable);
    ReviewResponse saveReview(Long movieId, ReviewRequest reviewRequest);
    ReviewResponse updateReview(UUID id, ReviewRequest reviewRequest);
    void deleteReview(UUID id);
}
