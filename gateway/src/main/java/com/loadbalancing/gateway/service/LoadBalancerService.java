package com.loadbalancing.gateway.service;


import com.loadbalancing.gateway.model.ServerInstance;
import com.loadbalancing.gateway.registry.ServerRegistry;
import com.loadbalancing.gateway.strategy.LoadBalancingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadBalancerService {
    private final LoadBalancingStrategy strategy;
    private final ServerRegistry serverRegistry;
    private final MetricService metricService;
    private final ProxyService proxyService;
    private final HealthService healthService;


    private List<ServerInstance>availableHealthyServers(){
        return new ArrayList<>(serverRegistry.getHealthyServers());
    }

    private ServerInstance getNextServer(List<ServerInstance>servers){
        return strategy.getNextServer(servers);
    }

    public String forwardRequest(String endpoint){
        List<ServerInstance> goodServers=availableHealthyServers();
        while (!goodServers.isEmpty()) {
            ServerInstance server = getNextServer(goodServers);
            metricService.requestStarted(server);
            try {
                String response=proxyService.requestForward(server,endpoint);
                healthService.resetFailure(server);
                return response;
            } catch (RestClientException e){
                metricService.requestFailed(server);
                healthService.handleFailure(server);
                goodServers.remove(server);
            } finally {
                metricService.requestCompleted(server);
            }
        }
    throw new RuntimeException("no active servers");
    }
}
