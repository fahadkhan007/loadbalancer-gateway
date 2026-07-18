package com.loadbalancing.gateway.service;

import com.loadbalancing.gateway.dto.GatewayMetricResponse;
import com.loadbalancing.gateway.dto.MetricsResponse;
import com.loadbalancing.gateway.dto.ServerMetricResponse;
import com.loadbalancing.gateway.registry.ServerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import com.loadbalancing.gateway.model.ServerInstance;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final ServerRegistry serverRegistry;

    public void requestStarted(ServerInstance server){
        server.getActiveConnections().incrementAndGet();
        server.getTotalRequests().incrementAndGet();
    }
    public void requestCompleted(ServerInstance server){

        server.getActiveConnections().decrementAndGet();
    }
    public void requestFailed(ServerInstance server){

        server.getFailedRequests().incrementAndGet();
    }

    public MetricsResponse getMetrics(){

        List<ServerInstance>serverInstances=serverRegistry.getServers();
        List<ServerMetricResponse>servers=serverInstances.stream()
                .map(serverInstance -> new ServerMetricResponse(
                        serverInstance.getUrl(),
                        serverInstance.isHealthy(),
                        serverInstance.getActiveConnections().get(),
                        serverInstance.getTotalRequests().get(),
                        serverInstance.getFailedRequests().get(),
                        serverInstance.getConsecutiveFailures().get()
                ))
                .toList();

        int totalRequests=serverInstances.stream()
                .mapToInt(server-> server.getTotalRequests().get())
                .sum();

        int failedRequests=serverInstances.stream()
                .mapToInt(server->server.getFailedRequests().get())
                .sum();

        int successfulRequests=totalRequests-failedRequests;

        int healthyServers=(int)serverInstances.stream().filter(ServerInstance::isHealthy).count();

        int unhealthyServers=servers.size()-healthyServers;

        GatewayMetricResponse gateway=new GatewayMetricResponse(
                totalRequests,
                successfulRequests,
                failedRequests,
                healthyServers,
                unhealthyServers
        );

        return new MetricsResponse(gateway, servers);

    }

}
