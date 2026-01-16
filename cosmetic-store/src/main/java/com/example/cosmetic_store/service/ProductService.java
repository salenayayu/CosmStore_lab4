package com.example.cosmetic_store.service;

import com.example.cosmetic_store.model.Product;
import com.example.cosmetic_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

     @Transactional
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        
        String changes = String.format("Created product: %s", product.getName());
        auditService.sendAuditEvent(
            AuditLog.OperationType.CREATE,
            "Product",
            savedProduct.getId(),
            changes
        );
        
        return savedProduct;
    }

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        String oldState = objectMapper.writeValueAsString(existingProduct);
        
        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());
        existingProduct.setBrand(productDetails.getBrand());
        existingProduct.setCategory(productDetails.getCategory());
        
        Product updatedProduct = productRepository.save(existingProduct);
        
        String newState = objectMapper.writeValueAsString(updatedProduct);
        String changes = String.format("Updated product %d. Old: %s, New: %s", 
            id, oldState, newState);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.UPDATE,
            "Product",
            id,
            changes
        );
        
        return updatedProduct;
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        String productInfo = objectMapper.writeValueAsString(product);
        productRepository.delete(product);
        
        auditService.sendAuditEvent(
            AuditLog.OperationType.DELETE,
            "Product",
            id,
            "Deleted product: " + productInfo
        );
    }
    
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
