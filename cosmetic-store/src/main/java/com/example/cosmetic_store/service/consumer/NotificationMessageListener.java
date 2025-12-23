package com.example.cosmetic_store.service.consumer;

import com.example.cosmetic_store.model.AuditLog;
import com.example.cosmetic_store.model.dto.AuditEvent;
import com.example.cosmetic_store.model.dto.NotificationEvent;
import com.example.cosmetic_store.model.Product;
import com.example.cosmetic_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationMessageListener {
    
    private final JavaMailSender mailSender;
    private final ProductRepository productRepository;
    
    @Value("${notification.recipients:admin@example.com}")
    private String[] recipients;
    
    @Value("${notification.low-stock-threshold:10}")
    private int stockThreshold;
    
    @JmsListener(destination = "notification.queue")
    public void process(NotificationEvent event) {
        if (event == null || event.getAuditEvent() == null) return;
        
        try {
            if (shouldNotify(event)) {
                sendEmail(event);
            }
        } catch (Exception e) {
            log.error("Notification error", e);
        }
    }
    
    private boolean shouldNotify(NotificationEvent event) {
        AuditEvent audit = event.getAuditEvent();
        
        // Низкий остаток
        if ("Product".equals(audit.getEntityName()) && 
            audit.getOperationType() == AuditLog.OperationType.UPDATE &&
            audit.getEntityId() != null) {
            
            Optional<Product> product = productRepository.findById(audit.getEntityId());
            if (product.isPresent() && product.get().getQuantity() <= stockThreshold) {
                event.setNotificationType("LOW_STOCK");
                return true;
            }
        }
        
        // Удаление бренда
        if ("Brand".equals(audit.getEntityName()) && 
            audit.getOperationType() == AuditLog.OperationType.DELETE) {
            event.setNotificationType("BRAND_DELETED");
            return true;
        }
        
        return false;
    }
    
    private void sendEmail(NotificationEvent event) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(recipients);
            msg.setSubject("Alert: " + event.getNotificationType());
            msg.setText("Event: " + event.getNotificationType());
            mailSender.send(msg);
            log.info("Sent: {}", event.getNotificationType());
        } catch (Exception e) {
            log.error("Email failed", e);
        }
    }
}