package com.loadbalancing.gateway.service;

import org.springframework.stereotype.Service;

import com.loadbalancing.gateway.model.ServerInstance;

@Service
public class MetricService {

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
}
