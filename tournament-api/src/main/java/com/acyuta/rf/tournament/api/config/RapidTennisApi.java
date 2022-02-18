package com.acyuta.rf.tournament.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "livescore-api")
@Data
public class RapidTennisApi {
    String baseUrl;
    String category;
    String xRapidapiKey;
}
