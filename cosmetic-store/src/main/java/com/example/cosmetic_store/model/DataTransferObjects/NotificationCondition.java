package com.example.cosmetic_store.model.dto;

import lombok.Data;

@Data
public class NotificationCondition {
    private ConditionType type;
    private String entityName;
    private String propertyName;
    private String stringValue;
    private Double minValue;
    private Double maxValue;
    private OperationType operationType;
    
    public enum ConditionType {
        ENTITY_NAME_CONTAINS,  
        ATTRIBUTE_OUT_OF_RANGE,
        SPECIFIC_ENTITY_CHANGE,
        PRICE_CHANGE,         
        LOW_STOCK            
    }
    
    public enum OperationType {
        CREATE, UPDATE, DELETE, ALL
    }
}