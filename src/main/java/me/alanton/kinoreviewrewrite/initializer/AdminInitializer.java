package me.alanton.kinoreviewrewrite.initializer;

import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.request.ActorRequest;
import me.alanton.kinoreviewrewrite.dto.request.RoleRequest;
import me.alanton.kinoreviewrewrite.entity.Actor;
import me.alanton.kinoreviewrewrite.entity.Role;
import me.alanton.kinoreviewrewrite.service.ActorService;
import me.alanton.kinoreviewrewrite.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor

@Component
@Order(2)
public class AdminInitializer implements CommandLineRunner {
    private final ActorService actorService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (actorService.existByUsername("admin")) {
            return;
        }

        ActorRequest actorRequest = ActorRequest.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(Set.of(new RoleRequest("USER"), new RoleRequest("ADMIN")))
                .build();

        actorService.saveActor(actorRequest);
    }
}
