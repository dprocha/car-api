package com.vmware.tanzu.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisRepositories
@RefreshScope
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
