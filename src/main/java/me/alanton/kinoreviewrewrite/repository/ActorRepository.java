package me.alanton.kinoreviewrewrite.repository;

import me.alanton.kinoreviewrewrite.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Optional<Actor> findByUsername(String username);

    boolean existsByUsername(String username);
}
