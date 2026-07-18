package com.loadbalancing.gateway.controller;

import com.loadbalancing.gateway.dto.MetricsResponse;
import com.loadbalancing.gateway.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MetricsController {
    private final MetricService metricService;

    @GetMapping("/metrics")
    public MetricsResponse getMetrics(){
        return metricService.getMetrics();
    }
}
