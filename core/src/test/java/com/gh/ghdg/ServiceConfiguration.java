package com.gh.ghdg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EntityScan(basePackages = "com.gh.ghdg.sysMgr.bean.entities")
@EnableMongoRepositories(basePackages = "com.gh.ghdg.businessMgr.Repository")
@EnableJpaRepositories(basePackages = "com.gh.ghdg.sysMgr.core.dao")
@TestPropertySource("classpath:application-test.properties")
@EnableTransactionManagement
@EnableJpaAuditing
@EnableMongoAuditing
public class ServiceConfiguration {
    public static void main(String[] args){
        SpringApplication.run(ServiceConfiguration.class);
    }
}
