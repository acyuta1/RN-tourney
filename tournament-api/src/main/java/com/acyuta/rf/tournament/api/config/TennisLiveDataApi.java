package com.acyuta.rf.tournament.api.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tennis-live-data-api")
@Data
public class TennisLiveDataApi {
    String baseUrl;
}
