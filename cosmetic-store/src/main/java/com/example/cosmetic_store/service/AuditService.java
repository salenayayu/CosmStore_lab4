package com.example.cosmetic_store.service;

import com.example.cosmetic_store.model.dto.AuditEvent;
import com.example.cosmetic_store.model.AuditLog.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {
    
    private final JmsTemplate jmsTemplate;
    
    public void sendAuditEvent(OperationType operationType, 
                              String entityName, 
                              Long entityId, 
                              String changes) {
        
        AuditEvent event = new AuditEvent();
        event.setOperationType(operationType);
        event.setEntityName(entityName);
        event.setEntityId(entityId);
        event.setChanges(changes);
        event.setTimestamp(LocalDateTime.now());
        event.setPerformedBy("SYSTEM");
        
        jmsTemplate.convertAndSend("audit.queue", event);
    }
}