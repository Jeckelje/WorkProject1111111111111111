package sudexpert.gov.by.workproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;
@OpenAPIDefinition(
        info = @Info(
                title = "Work project API",
                version = "1.0",
                description = "API for managing workers"
        ),
        servers = @Server(
                url = "http://localhost:8080",
                description = "Local server"
        )
)

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class    WorkProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkProjectApplication.class, args);
    }
    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
