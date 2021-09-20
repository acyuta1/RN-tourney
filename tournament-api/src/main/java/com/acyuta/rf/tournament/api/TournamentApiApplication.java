package com.acyuta.rf.tournament.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.acyuta.rf.tournament.core","com.acyuta.rf.tournament.api"})
@EnableRabbit
@ConfigurationPropertiesScan
@EnableJpaRepositories(basePackages = {"com.acyuta.rf.tournament.core"})
@EntityScan(basePackages = {"com.acyuta.rf.tournament.core"})
public class TournamentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournamentApiApplication.class, args);
    }
}
