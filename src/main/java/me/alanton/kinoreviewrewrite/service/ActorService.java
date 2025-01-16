package me.alanton.kinoreviewrewrite.service;

import me.alanton.kinoreviewrewrite.dto.request.ActorRequest;
import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface ActorService extends UserDetailsService {
    ActorResponse getActor(UUID id);
    Page<ActorResponse> getActors(Pageable pageable);
    ActorResponse saveActor(ActorRequest actorRequest);
    boolean existByUsername(String username);
}
