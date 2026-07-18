package com.loadbalancing.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MetricsResponse {

    private GatewayMetricResponse gateway;

    private List<ServerMetricResponse>servers;

}
