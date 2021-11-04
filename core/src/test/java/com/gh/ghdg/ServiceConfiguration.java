package com.gh.ghdg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com/gh/ghdg/bean/entities")
@EnableJpaRepositories(basePackages = "com.gh.ghdg.sysMgr.core.dao")
public class ServiceConfiguration {
    public static void main(String[] args){
        SpringApplication.run(ServiceConfiguration.class);
    }
}
