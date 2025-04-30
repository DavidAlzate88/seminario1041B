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
				@Server(description = "ambiente local", url = "http://localhost:8080/api/compras"),
				@Server(description = "ambiente aws", url = "http://ec2-3-21-12-76.us-east-2.compute.amazonaws.com:8085/api/compras")
		}
)

@SpringBootApplication
public class Seminario1041BApplication {

	public static void main(String[] args) {
		SpringApplication.run(Seminario1041BApplication.class, args);
	}

}
