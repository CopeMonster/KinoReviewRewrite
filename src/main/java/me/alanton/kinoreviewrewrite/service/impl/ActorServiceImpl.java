package me.alanton.kinoreviewrewrite.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alanton.kinoreviewrewrite.dto.request.ActorRequest;
import me.alanton.kinoreviewrewrite.dto.response.ActorResponse;
import me.alanton.kinoreviewrewrite.entity.Actor;
import me.alanton.kinoreviewrewrite.entity.Role;
import me.alanton.kinoreviewrewrite.exception.BusinessException;
import me.alanton.kinoreviewrewrite.exception.BusinessExceptionReason;
import me.alanton.kinoreviewrewrite.mapper.ActorMapper;
import me.alanton.kinoreviewrewrite.repository.ActorRepository;
import me.alanton.kinoreviewrewrite.repository.RoleRepository;
import me.alanton.kinoreviewrewrite.service.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;
    private final RoleRepository roleRepository;

    @Override
    public ActorResponse getActor(UUID id) {
        log.info("Fetching actor with id: {}", id);
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Actor with id: {} not found", id);
                    return new BusinessException(BusinessExceptionReason.ACTOR_NOT_FOUND);
                });
        log.debug("Fetched actor: {}", actor);
        return actorMapper.toActorResponse(actor);
    }

    @Override
    public Page<ActorResponse> getActors(Pageable pageable) {
        log.info("Fetching all actors with pagination: {}", pageable);
        Page<Actor> actors = actorRepository.findAll(pageable);
        log.debug("Fetched actors: {}", actors);
        return actors.map(actorMapper::toActorResponse);
    }

    @Override
    public ActorResponse saveActor(ActorRequest actorRequest) {
        log.info("Saving new actor: {}", actorRequest.username());
        Set<Role> roles = actorRequest.roles()
                .stream()
                .map(roleRequest -> roleRepository.findByName(roleRequest.name())
                        .orElseThrow(() -> {
                            log.error("Role {} not found", roleRequest.name());
                            return new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
                        }))
                .collect(Collectors.toSet());

        Actor actor = Actor.builder()
                .username(actorRequest.username())
                .email(actorRequest.email())
                .password(actorRequest.password())
                .roles(roles)
                .verified(false)
                .reviews(new ArrayList<>())
                .build();

        actorRepository.save(actor);
        log.info("Actor saved with id: {}", actor.getId());

        return actorMapper.toActorResponse(actor);
    }

    @Override
    public boolean existByUsername(String username) {
        log.info("Checking existence of actor by username: {}", username);
        return actorRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        return actorRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Actor with username: {} not found", username);
                    return new BusinessException(BusinessExceptionReason.ACTOR_NOT_FOUND);
                });
    }
}
