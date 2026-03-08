package com.project.api_gateway.controller;

import com.project.api_gateway.repository.ApiRequestLogRepository;
import com.project.api_gateway.service.ApiLoggingService;
import com.project.api_gateway.service.GatewayRoutingService;
import com.project.api_gateway.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GatewayController {

    private final GatewayRoutingService routingService;
    private final RateLimiterService rateLimiter;
    private final ApiLoggingService loggingService;


    @PostMapping("/users")
    public ResponseEntity<?> createUser(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody Object body) {

        long start = System.currentTimeMillis();

        if (!rateLimiter.isAllowed(apiKey)) {
            return ResponseEntity.status(429).body("Too Many Requests");
        }

        String response = routingService.forwardToUserService("/users", body);

        long responseTime = System.currentTimeMillis() - start;

        loggingService.logRequest(
                apiKey,
                "/api/users",
                "POST",
                200,
                responseTime
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/notifications/{userId}")
    public String getNotifications(@PathVariable String userId) {

        return routingService.forwardToNotificationService("/notifications/" + userId);
    }
}