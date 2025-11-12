package com.hc.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {


    @Bean
    public OpenAPI projectOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpringDoc API")
                        .description("Spring Boot 3 集成 SpringDoc 的示例文档")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://github.com/springdoc")))
                .externalDocs(new ExternalDocumentation()
                        .description("更多学习资料")
                        .url("https://springdoc.org"));
    }
}