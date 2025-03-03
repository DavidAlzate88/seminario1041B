package co.edu.uniajc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Backend seminario1041B",
				version = "1.0.0",
				description = "APIs Swagger Backend seminario1041B",
				license = @License(name = "Apache 2.0"),
				contact = @Contact(url = "S", name = "MS-nameMS", email = "")),
		security = {@SecurityRequirement(name = "")},
		servers = {
				@Server(description = "ambiente local", url = "http://localhost:8080/"),
				@Server(description = "url ambiente dev expuesta por Apigateway", url = ""),
				@Server(description = "url ambiente qa expuesta por Apigateway", url = "https://"),
				@Server(description = "url ambiente prod expuesta por Apigateway", url = "https:/")

		}
)

@SpringBootApplication
public class Seminario1041BApplication {

	public static void main(String[] args) {
		SpringApplication.run(Seminario1041BApplication.class, args);
	}

}
