package co.edu.uniajc.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Backend seminario1041B",
                version = "1.0.0",
                description = "APIs Swagger Backend seminario1041B"
                ),
        security = {@SecurityRequirement(name = "")},
        servers = {
                @Server(description = "ambiente local", url = "http://localhost:8080/"),

        }
)
public class OpenApiConfig {
}