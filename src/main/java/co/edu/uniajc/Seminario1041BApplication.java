package co.edu.uniajc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Backend seminario1041B",
				version = "1.0.0",
				description = "APIs Swagger Backend seminario1041B"),
		servers = {
				@Server(description = "ambiente local", url = "http://localhost:8080/")
		}
)

@SpringBootApplication
public class Seminario1041BApplication {

	public static void main(String[] args) {
		SpringApplication.run(Seminario1041BApplication.class, args);
	}

}
