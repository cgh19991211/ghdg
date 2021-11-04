package com.gh.ghdg.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableCaching
@ComponentScan(basePackages = "com.gh.ghdg")
@EntityScan(basePackages = "com.gh.ghdg")
@EnableJpaRepositories(basePackages = "com.gh.ghdg")
@EnableJpaAuditing
@SpringBootApplication
@EnableOpenApi
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
