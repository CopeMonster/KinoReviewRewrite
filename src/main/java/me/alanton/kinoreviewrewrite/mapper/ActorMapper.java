package me.alanton.kinoreviewrewrite.mapper;

import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import me.alanton.kinoreviewrewrite.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ActorMapper {
    ActorResponse toActorResponse(Actor actor);
    List<ActorResponse> toActorResponseList(List<Actor> actors);
}
