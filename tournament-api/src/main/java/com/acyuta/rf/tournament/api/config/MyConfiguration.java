package com.acyuta.rf.tournament.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("example")
public class MyConfiguration {

    private String username;
    private String user;

    private String xyz;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXyz() {
        return xyz;
    }

    public void setXyz(String xyz) {
        this.xyz = xyz;
    }
}