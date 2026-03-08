package com.project.api_gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GatewayRoutingService {

    private final WebClient.Builder webClientBuilder;

    public GatewayRoutingService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String forwardToUserService(String path, Object body) {

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081" + path)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String forwardToNotificationService(String path) {

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082" + path)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}