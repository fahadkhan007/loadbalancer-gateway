package com.loadbalancing.gateway.controller;

import com.loadbalancing.gateway.configuration.AppConfig;
import com.loadbalancing.gateway.model.ServerInstance;
import com.loadbalancing.gateway.service.LoadBalancerService;
import com.loadbalancing.gateway.strategy.LoadBalancingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class GatewayController {
    private final LoadBalancerService loadBalancerService;

    @GetMapping("/gateway/hello")
    public String hello(){
        return loadBalancerService.forwardRequest("api/hello");
    }
}
