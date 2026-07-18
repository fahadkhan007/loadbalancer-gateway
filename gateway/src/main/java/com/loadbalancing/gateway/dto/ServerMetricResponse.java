package com.loadbalancing.gateway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerMetricResponse {
    private String url;
    private boolean healthy;
    private int activeConnections;
    private int totalRequests;
    private int failedRequests;
    private int consecutiveFailures;
}
