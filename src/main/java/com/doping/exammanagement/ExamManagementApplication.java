package com.doping.exammanagement;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class ExamManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamManagementApplication.class, args);
    }

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI().info(apiInfo());
    }

    public Info apiInfo() {
        Info info = new Info();

        info
                .title("Exam Management API")
                .description("EXAM MANAGEMENT API Documentation")
                .version("1.0.0");

        return info;
    }

}
