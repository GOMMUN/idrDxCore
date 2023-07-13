package com.idr.pdd.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig{

	@Bean
    public OpenAPI maapCoreAPI() {
        String[] paths = {"/v1/**"};

        return new OpenAPI()
                .info(new Info().title("Data Share Platform MAAP Core")
                                 .description("Data Share Platform MAAP Core API 명세")
                                 .version("v1"));
    }
}
