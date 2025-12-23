package com.example.cosmetic_store.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Data  // авто геттеры и сеттеры тут
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType operationType;
    
    @Column(nullable = false)
    private String entityName;
    
    private Long entityId;
    
    @Column(columnDefinition = "TEXT")
    private String changes;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    private String performedBy;
    
    public enum OperationType {
        CREATE, UPDATE, DELETE
    }
}