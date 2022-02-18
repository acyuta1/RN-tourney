package com.acyuta.rf.tournament.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.acyuta.rf.tournament.core","com.acyuta.rf.tournament.api"})
@EnableRabbit
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = {"com.acyuta.rf.tournament.core"})
@EntityScan(basePackages = {"com.acyuta.rf.tournament.core"})
@EnableFeignClients(basePackages = "com.acyuta.rf.rafantasyShared.feign")
public class TournamentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournamentApiApplication.class, args);
    }
}
