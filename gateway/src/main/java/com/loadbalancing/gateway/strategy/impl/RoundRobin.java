package com.loadbalancing.gateway.strategy.impl;

import com.loadbalancing.gateway.strategy.LoadBalancingStrategy;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobin implements LoadBalancingStrategy {

    private final AtomicInteger counter=new AtomicInteger();

    @Override
    public String getNextServer(List<String>servers){
        int index=counter.getAndIncrement()%servers.size();
        return servers.get(index);
    }
}
