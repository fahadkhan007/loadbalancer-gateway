package com.loadbalancing.gateway.service;


import com.loadbalancing.gateway.configuration.ServerConfig;
import com.loadbalancing.gateway.strategy.LoadBalancingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadBalancerService {
    private final LoadBalancingStrategy strategy;
    private final ServerConfig serverConfig;

    public String getNextServer(){
        return strategy.getNextServer(serverConfig.getServers());
    }
}
