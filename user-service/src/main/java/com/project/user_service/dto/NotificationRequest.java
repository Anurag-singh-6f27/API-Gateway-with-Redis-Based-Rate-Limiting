package com.project.user_service.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private String userId;
    private String message;
    private boolean read;

}