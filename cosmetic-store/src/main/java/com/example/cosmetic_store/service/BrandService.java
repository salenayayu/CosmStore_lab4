package com.example.cosmetic_store.service;

import com.example.cosmetic_store.model.Brand;
import com.example.cosmetic_store.repository.BrandRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    
    private final BrandRepository brandRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
    
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }
    
    @Transactional
    public Brand createBrand(Brand brand) {
        Brand savedBrand = brandRepository.save(brand);
        
        String changes = String.format("Created brand: %s", brand.getName());
        auditService.sendAuditEvent(
            AuditLog.OperationType.CREATE,
            "Brand",
            savedBrand.getId(),
            changes
        );
        
        return savedBrand;
    }
    
    @Transactional
    public Brand updateBrand(Long id, Brand brandDetails) {
        Brand existingBrand = brandRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        String oldState = objectMapper.writeValueAsString(existingBrand);
        
        existingBrand.setName(brandDetails.getName());
        existingBrand.setCountry(brandDetails.getCountry());
        existingBrand.setDescription(brandDetails.getDescription());
        
        Brand updatedBrand = brandRepository.save(existingBrand);
        
        String newState = objectMapper.writeValueAsString(updatedBrand);
        String changes = String.format("Updated brand %d. Old: %s, New: %s", 
            id, oldState, newState);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.UPDATE,
            "Brand",
            id,
            changes
        );
        
        return updatedBrand;
    }
    
    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        String brandInfo = objectMapper.writeValueAsString(brand);
        
        brandRepository.delete(brand);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.DELETE,
            "Brand",
            id,
            "Deleted brand: " + brandInfo
        );
    }
    
    // для совместимости
    public Brand save(Brand brand) {
        return createBrand(brand);
    }
    
    public void deleteById(Long id) {
        deleteBrand(id);
    }
}