package com.example.newsfeed.common;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "Authorization",
        description = "access Token을 입력해주세요.",
        in = SecuritySchemeIn.HEADER)

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
                                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                        .name("Authorization")
                                        .description("Token을 입력해주세요.")
                        )
                )
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("Authorization", Collections.emptyList()));
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] packages = {"com.example.newsfeed"};
        return GroupedOpenApi.builder()
                .group("springdoc-openapi")
                .packagesToScan(packages)
                .build();
    }

    private Info apiInfo() {
        String description = "뉴스피드 API 입니다";
        return new Info()
                .title("뉴스피드 api")
                .description(description)
                .version("2.5");
    }
}
