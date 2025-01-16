package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.dto.request.ReviewRequest;
import me.alanton.kinoreviewrewrite.dto.response.ReviewResponse;
import me.alanton.kinoreviewrewrite.entity.Actor;
import me.alanton.kinoreviewrewrite.entity.Movie;
import me.alanton.kinoreviewrewrite.entity.Review;
import me.alanton.kinoreviewrewrite.exception.BusinessException;
import me.alanton.kinoreviewrewrite.exception.BusinessExceptionReason;
import me.alanton.kinoreviewrewrite.mapper.ReviewMapper;
import me.alanton.kinoreviewrewrite.repository.MovieRepository;
import me.alanton.kinoreviewrewrite.repository.ReviewRepository;
import me.alanton.kinoreviewrewrite.service.ReviewService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final MovieRepository movieRepository;

    @Override
    @Cacheable(value = "reviews", key = "#id")
    public ReviewResponse getReviewById(UUID id) {
        log.info("Fetching review with id: {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Review with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.REVIEW_NOT_FOUND);
                });

        log.debug("Fetched review: {}", review);
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @Transactional
    @Cacheable(value = "reviews", key = "#movieId")
    public Page<ReviewResponse> getMovieReviews(Long movieId, Pageable pageable) {
        log.info("Fetching reviews for movie with id: {} with pagination: {}", movieId, pageable);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> {
                    log.error("Movie with id: {} not found", movieId);
                    return new BusinessException(BusinessExceptionReason.MOVIE_NOT_FOUND);
                });

        Page<Review> reviews = reviewRepository.findByMovie(movie, pageable);
        log.debug("Fetched reviews: {}", reviews);

        return reviews.map(reviewMapper::toReviewResponse);
    }

    @Override
    @Transactional
    public ReviewResponse saveReview(Long movieId, ReviewRequest reviewRequest) {
        log.info("Creating new review for movie with id: {}", movieId);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> {
                    log.error("Movie with id: {} not found", movieId);
                    return new BusinessException(BusinessExceptionReason.MOVIE_NOT_FOUND);
                });

        Actor actor = (Actor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Creating review for actor: {}", actor.getUsername());

        Review review = Review.builder()
                .title(reviewRequest.title())
                .pros(reviewRequest.pros())
                .cons(reviewRequest.cons())
                .description(reviewRequest.description())
                .rating(reviewRequest.rating())
                .movie(movie)
                .actor(actor)
                .build();

        reviewRepository.save(review);

        log.info("Saved review with id: {}", review.getId());
        log.debug("Saved review: {}", review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(UUID id, ReviewRequest reviewRequest) {
        log.info("Updating review with id: {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Review with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.REVIEW_NOT_FOUND);
                });

        Actor actor = (Actor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Actor {} is attempting to update review", actor.getUsername());

        if (!review.getActor().getId().equals(actor.getId())) {
            log.error("Actor {} is not authorized to update review with id: {}", actor.getUsername(), id);
            throw new BusinessException(BusinessExceptionReason.UNAUTHORIZED_ACTION);
        }

        review.setTitle(reviewRequest.title());
        review.setPros(reviewRequest.pros());
        review.setCons(reviewRequest.cons());
        review.setDescription(reviewRequest.description());
        review.setRating(reviewRequest.rating());

        reviewRepository.save(review);

        log.info("Updated review with id: {}", review.getId());
        log.debug("Updated review: {}", review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @Transactional
    public void deleteReview(UUID id) {
        log.info("Deleting review with id: {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Review with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.REVIEW_NOT_FOUND);
                });

        Actor actor = (Actor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Actor {} is attempting to delete review", actor.getUsername());

        if (!review.getActor().getId().equals(actor.getId())) {
            log.error("Actor {} is not authorized to delete review with id: {}", actor.getUsername(), id);
            throw new BusinessException(BusinessExceptionReason.UNAUTHORIZED_ACTION);
        }

        reviewRepository.deleteById(id);
        review.getMovie().getReviews().remove(review);
        review.getActor().getReviews().remove(review);

        log.info("Deleted review with id: {}", id);
    }
}
