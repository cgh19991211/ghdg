package com.gh.ghdg.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@ComponentScan(basePackages = "com.gh.ghdg")
@EntityScan(basePackages = "com.gh.ghdg.sysMgr.bean.entities")
@EnableMongoRepositories(basePackages = "com.gh.ghdg.businessMgr")
@EnableJpaRepositories(basePackages = "com.gh.ghdg.sysMgr")
@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
