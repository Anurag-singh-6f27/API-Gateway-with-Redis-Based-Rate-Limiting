package com.project.api_gateway.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private static final int LIMIT = 10;

    private final RedisTemplate<String, Integer> redisTemplate;

    public RateLimiterService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String apiKey) {

        String key = "rate_limit:" + apiKey;

        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(60));
        }

        return count <= LIMIT;
    }
}