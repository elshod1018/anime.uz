package uz.anime;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import uz.anime.config.security.SessionUser;

import java.util.Optional;
import java.util.Random;
@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class AnimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimeApplication.class, args);
    }
    @Bean
    public AuditorAware<Integer> auditorAware(SessionUser sessionUser) {
        return () -> Optional.of(sessionUser.id());
    }
    @Bean
    public Random random() {
        return new Random();
    }
}
