package com.example.cosmetic_store.model.dto;

import lombok.Data;
import java.util.Map;

@Data
public class NotificationEvent {
    private AuditEvent auditEvent;
    private String notificationType;
    private Map<String, Object> additionalData;
    private String[] recipients;

     // конструктор
    public NotificationEvent(AuditEvent auditEvent, String notificationType) {
        this.auditEvent = auditEvent;
        this.notificationType = notificationType;
        this.additionalData = new HashMap<>();
        this.recipients = new String[]{"default@example.com"};
    }
}