package com.ravinder.api.post.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwaggerConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title("Post Service API")
                        .version("1.0")
                        .description("API for managing posts in the Microservices Blog Platform"));
    }
}
