package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "controller","service","service.impl","repository","entity","dto","mapper","exception"
})
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "repository")
public class Checkpoint2Application {
    public static void main(String[] args) {
        SpringApplication.run(Checkpoint2Application.class, args);
    }
}
