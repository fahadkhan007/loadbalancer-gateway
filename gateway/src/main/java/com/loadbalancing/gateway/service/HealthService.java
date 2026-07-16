package com.loadbalancing.gateway.service;

import com.loadbalancing.gateway.DTO.HealthResponse;
import com.loadbalancing.gateway.model.ServerInstance;
import com.loadbalancing.gateway.registry.ServerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final ServerRegistry serverRegistry;
    private final RestTemplate restTemplate;

    private static final int FAILURE_THRESHOLD=3;
    private static final long HEALTH_CHECK_INTERVAL=10000;
    private static final String HEALTH_ENDPOINT="/actuator/health";

    private List<ServerInstance>totalUnhealthyServers(){
        return new ArrayList<>(serverRegistry.getUnhealthyServers());
    }

    @Scheduled(fixedDelay = HEALTH_CHECK_INTERVAL)
    public void checkHealth(){
        List<ServerInstance>servers=totalUnhealthyServers();
        for (ServerInstance server : servers){
            String url=server.getUrl();
            try {
                HealthResponse healthResponse=restTemplate.getForObject(
                        url+HEALTH_ENDPOINT,
                        HealthResponse.class
                );
                if(healthResponse!=null && "UP".equals(healthResponse.getStatus())){
                    resetFailure(server);
                }
            }catch (RestClientException e){
                server.setHealthy(false);
            }
        }
    }

    public void handleFailure(ServerInstance server){
        server.getConsecutiveFailures().incrementAndGet();
        if(server.getConsecutiveFailures().get()>=FAILURE_THRESHOLD){
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
