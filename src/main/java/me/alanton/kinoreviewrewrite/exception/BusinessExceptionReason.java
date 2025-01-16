package me.alanton.kinoreviewrewrite.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.alanton.kinoreviewrewrite.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessExceptionReason implements BusinessExceptionPolicy {
    MOVIE_NOT_FOUND("Movie not found", HttpStatus.NOT_FOUND),
    GENRE_NOT_FOUND("Genre not found", HttpStatus.NOT_FOUND),
    ACTOR_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND("Review not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_ACTION("Unauthorized action performed", HttpStatus.UNAUTHORIZED);

    private final String code = BusinessExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;
}
