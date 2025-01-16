package me.alanton.kinoreviewrewrite.initializer;

import lombok.RequiredArgsConstructor;
import me.alanton.kinoreviewrewrite.dto.response.RoleResponse;
import me.alanton.kinoreviewrewrite.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
@Order(1)
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final String[] defaultRoles = {
            "USER",
            "ADMIN"
    };

    @Override
    public void run(String... args) throws Exception {
        for (String role : defaultRoles) {
            if (!roleService.existByName(role)) {
                roleService.saveRole(new RoleResponse(role));
            }
        }
    }
}
