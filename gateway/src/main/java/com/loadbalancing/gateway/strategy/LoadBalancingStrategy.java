package com.loadbalancing.gateway.strategy;

import java.util.List;

public interface LoadBalancingStrategy {
    String getNextServer(List<String>servers);
}
