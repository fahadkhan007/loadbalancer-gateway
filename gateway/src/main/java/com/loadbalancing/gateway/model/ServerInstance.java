package com.loadbalancing.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class ServerInstance {
    private String url;

    private boolean healthy;

    private AtomicInteger activeConnections;

    private AtomicInteger totalRequests;

    private AtomicInteger failedRequests;

    private AtomicInteger consecutiveFailures;

    private int weight;
}
