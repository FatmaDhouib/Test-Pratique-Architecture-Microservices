package com.boutique.avisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients   // ← Active les clients Feign
public class AvisServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvisServiceApplication.class, args);
    }
}