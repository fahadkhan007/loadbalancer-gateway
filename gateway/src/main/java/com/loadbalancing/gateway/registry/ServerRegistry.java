package com.loadbalancing.gateway.registry;

import com.loadbalancing.gateway.model.ServerInstance;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerRegistry {
    private final List<ServerInstance>servers=new ArrayList<>();

    @PostConstruct
    public void init(){
        servers.add(new ServerInstance(
                "http://localhost:8081",
                true,
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                1
        ));

        servers.add(new ServerInstance(
                "http://localhost:8082",
                true,
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                1
        ));

        servers.add(new ServerInstance(
                "http://localhost:8083",
                true,
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                new AtomicInteger(0),
                1
        ));
    }

    public List<ServerInstance>getServers(){
        return servers;
    }

    public List<ServerInstance>getHealthyServers(){
        return servers.stream().filter(ServerInstance::isHealthy).toList();
    }

    public List<ServerInstance>getUnhealthyServers(){
        return servers.stream().filter(serverInstance -> !serverInstance.isHealthy()).toList();
    }
}
