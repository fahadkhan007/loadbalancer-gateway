package com.loadbalancing.gateway.service;

import com.loadbalancing.gateway.model.ServerInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProxyService {
    private final RestTemplate restTemplate;

    public String requestForward(ServerInstance server,String endpoint){
        return restTemplate.getForObject(
                server.getUrl()+endpoint,
                String.class
        );
    }
}
