package com.loadbalancing.gateway.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ServerConfig {
    private final List<String>servers=List.of(
            "localhost:8081",
            "localhost:8082",
            "localhost:8083"
    );
    public List<String>getServers(){
        return servers;
    }
}
