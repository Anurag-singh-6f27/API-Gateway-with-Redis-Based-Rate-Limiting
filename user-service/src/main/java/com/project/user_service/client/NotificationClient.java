package com.project.user_service.client;

import com.project.user_service.dto.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationClient.class);

    private final WebClient webClient;

    public NotificationClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void sendNotification(String userId, String message) {

        log.info("Sending notification for user: {}", userId);

        NotificationRequest request = new NotificationRequest();
        request.setUserId(userId);
        request.setMessage(message);
        request.setRead(false);

        try {
            webClient.post()
                    .uri("/notifications")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            log.info("Notification sent successfully for user: {}", userId);

        } catch (Exception e) {
            log.error("Notification service unavailable for user: {}", userId, e);
        }
    }
}