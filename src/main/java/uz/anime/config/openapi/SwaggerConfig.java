package uz.anime.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static uz.anime.utils.UrlUtils.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Anime Uz")
                        .description("API for applicants enjoy animes")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Elshod Nuriddinov ")
                                .email("nuriddinovelshod2003@gmail.com")
                                .url("https://github.com/elshod1018"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Wikipedia Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .servers(List.of(
                                new Server().url("https://anime-uz.onrender.com").description("Production Server"),
                                new Server().url("http://localhost:8080").description("Development Server")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components((new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                ));
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch(BASE_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch(BASE_AUTH_URL + "/**")
                .build();
    }
}
