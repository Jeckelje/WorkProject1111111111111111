package sudexpert.gov.by.workproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
@OpenAPIDefinition(
        info = @Info(
                title = "Book Storage Service API",
                version = "1.0",
                description = "API for managing books storage"
        ),
        servers = @Server(
                url = "http://localhost:8080",
                description = "Local server"
        )
)

@SpringBootApplication
@EnableAspectJAutoProxy
public class WorkProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkProjectApplication.class, args);
    }
    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
