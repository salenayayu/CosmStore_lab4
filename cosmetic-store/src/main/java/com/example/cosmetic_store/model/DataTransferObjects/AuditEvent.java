package com.example.cosmetic_store.model.dto;

import com.example.cosmetic_store.model.AuditLog.OperationType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditEvent {
    private OperationType operationType;
    private String entityName;
    private Long entityId;
    private String changes;
    private LocalDateTime timestamp;
    private String performedBy;
}