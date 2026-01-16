package com.example.cosmetic_store.service.consumer;

import com.example.cosmetic_store.model.AuditLog;
import com.example.cosmetic_store.model.dto.AuditEvent;
import com.example.cosmetic_store.model.dto.NotificationEvent;
import com.example.cosmetic_store.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap; 
import java.util.Map; 

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditMessageListener {
    
    private final AuditLogRepository auditLogRepository;
    private final JmsTemplate jmsTemplate; 
    
    @JmsListener(destination = "store.events.topic",
                subscription = "audit-subscription",
                containerFactory = "jmsListenerContainerFactory",
                selector = "_type = 'auditEvent'")
    @Transactional
    public void receiveAuditEvent(AuditEvent event) {
        log.info("Received audit event: {}", event);
        
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setOperationType(event.getOperationType());
            auditLog.setEntityName(event.getEntityName());
            auditLog.setEntityId(event.getEntityId());
            auditLog.setChanges(event.getChanges());
            auditLog.setTimestamp(event.getTimestamp());
            auditLog.setPerformedBy(event.getPerformedBy());
            
            auditLogRepository.save(auditLog);
            log.info("Audit log saved successfully for entity: {}", event.getEntityName());
            
            // отправка события для проверки условий уведомлений
            sendToNotificationTopic(event);
            
        } catch (Exception e) {
            log.error("Failed to process audit event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process audit event", e);
        }
    }
    
    private void sendToNotificationTopic(AuditEvent event) {
        try {
            // уведомление на основе события аудита
            NotificationEvent notificationEvent = createNotificationEvent(event);
            jmsTemplate.convertAndSend("store.events.topic", notificationEvent);
            log.debug("Audit event forwarded to notification topic. Entity: {}", 
                    event.getEntityName());
            
        } catch (Exception e) {
            log.warn("Failed to send audit event to notification topic. Entity: {}, Error: {}", 
                    event.getEntityName(), e.getMessage());
        }
    }
    
    private NotificationEvent createNotificationEvent(AuditEvent auditEvent) {
        NotificationEvent event = new NotificationEvent();
        event.setAuditEvent(auditEvent);
        event.setNotificationType("AUDIT_EVENT");
        event.setAdditionalData(new HashMap<>()); 
        return event;
    }
}