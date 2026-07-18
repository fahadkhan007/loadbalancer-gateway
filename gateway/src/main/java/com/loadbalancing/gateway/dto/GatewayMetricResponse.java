package com.loadbalancing.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GatewayMetricResponse {
    private int totalRequests;
    private int successfulRequests;
    private int failedRequests;

    private int healthyServers;
    private int unhealthyServers;
}
