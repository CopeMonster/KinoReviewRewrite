package me.alanton.kinoreviewrewrite.mapper;

import me.alanton.kinoreviewrewrite.dto.response.ReviewResponse;
import me.alanton.kinoreviewrewrite.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    @Mapping(target = "movie", source = "review.movie.movieDetails")
    ReviewResponse toReviewResponse(Review review);
}
