package com.acyuta.rf.tournament.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableRabbit
@ConfigurationPropertiesScan
public class TournamentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournamentApiApplication.class, args);
    }
}
