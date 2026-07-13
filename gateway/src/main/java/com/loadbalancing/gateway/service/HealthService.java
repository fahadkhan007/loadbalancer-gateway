package com.loadbalancing.gateway.service;

import com.loadbalancing.gateway.model.ServerInstance;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private static final int FAILURE_THRESHOLD=3;

    public void handleFailure(ServerInstance server){
        server.getConsecutiveFailures().incrementAndGet();
        if(server.getConsecutiveFailures().get()>=3){
            server.setHealthy(false);
        }
    }

    public void resetFailure(ServerInstance server){
        server.getConsecutiveFailures().set(0);
        if(!server.isHealthy()){
            server.setHealthy(true);
        }
    }

}
