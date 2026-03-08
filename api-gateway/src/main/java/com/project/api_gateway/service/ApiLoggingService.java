package com.project.api_gateway.service;

import com.project.api_gateway.entity.ApiRequestLog;
import com.project.api_gateway.repository.ApiRequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApiLoggingService {

    private final ApiRequestLogRepository logRepository;

    public ApiLoggingService(ApiRequestLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logRequest(String apiKey,
                           String endpoint,
                           String method,
                           int statusCode,
                           long responseTime) {

        ApiRequestLog log = new ApiRequestLog();

        log.setApiKey(apiKey);
        log.setEndpoint(endpoint);
        log.setMethod(method);
        log.setStatusCode(statusCode);
        log.setTimestamp(LocalDateTime.now());
        log.setResponseTime(responseTime);

        logRepository.save(log);
    }
}