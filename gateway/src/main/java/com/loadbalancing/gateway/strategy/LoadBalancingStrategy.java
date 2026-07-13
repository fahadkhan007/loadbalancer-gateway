package com.loadbalancing.gateway.strategy;

import com.loadbalancing.gateway.model.ServerInstance;

import java.util.List;

public interface LoadBalancingStrategy {
    ServerInstance getNextServer(List<ServerInstance>servers);
}
