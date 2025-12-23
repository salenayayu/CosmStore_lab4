package com.example.cosmetic_store.service;

import com.example.cosmetic_store.model.Category;
import com.example.cosmetic_store.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    @Transactional
    public Category createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        
        String changes = String.format("Created category: %s", category.getName());
        auditService.sendAuditEvent(
            AuditLog.OperationType.CREATE,
            "Category",
            savedCategory.getId(),
            changes
        );
        
        return savedCategory;
    }
    
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        String oldState = objectMapper.writeValueAsString(existingCategory);
        
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());
        
        Category updatedCategory = categoryRepository.save(existingCategory);
        
        String newState = objectMapper.writeValueAsString(updatedCategory);
        String changes = String.format("Updated category %d. Old: %s, New: %s", 
            id, oldState, newState);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.UPDATE,
            "Category",
            id,
            changes
        );
        
        return updatedCategory;
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        
        String categoryInfo = objectMapper.writeValueAsString(category);
        
        categoryRepository.delete(category);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.DELETE,
            "Category",
            id,
            "Deleted category: " + categoryInfo
        );
    }
    
    // для совместимости
    public Category save(Category category) {
        return createCategory(category);
    }
    
    public void deleteById(Long id) {
        deleteCategory(id);
    }
}