package com.project.api_gateway.controller;

import com.project.api_gateway.repository.ApiRequestLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ApiRequestLogRepository repository;

    public AdminController(ApiRequestLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/usage")
    public long totalRequests() {
        return repository.count();
    }
}