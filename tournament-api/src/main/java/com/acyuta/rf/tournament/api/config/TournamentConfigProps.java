package com.acyuta.rf.tournament.api.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Data
public class TournamentConfigProps {
    private String uploadDir;
}
