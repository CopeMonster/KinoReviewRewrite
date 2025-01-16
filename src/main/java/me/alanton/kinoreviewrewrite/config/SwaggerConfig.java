package me.alanton.kinoreviewrewrite.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "KinoReview API",
                description = "API for reviewing movies",
                version = "1.0.0",
                contact = @Contact(
                        name = "CopeMonster",
                        email = "alankassymbek@gmail.com"
                )
        )
)
public class SwaggerConfig {

}
