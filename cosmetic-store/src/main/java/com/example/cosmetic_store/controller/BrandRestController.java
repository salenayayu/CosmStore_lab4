package com.example.cosmetic_store.controller;

import com.example.cosmetic_store.model.Brand;
import com.example.cosmetic_store.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandService brandService;
    // получение данных
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.findAll();
        return ResponseEntity.ok(brands);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Optional<Brand> brand = brandService.findById(id);
        return brand.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    // создание бренда
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand savedBrand = brandService.save(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }
    // обновление
    @PutMapping(value = "/{id}", 
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        if (!brandService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        brand.setId(id);
        Brand updatedBrand = brandService.save(brand);
        return ResponseEntity.ok(updatedBrand);
    }
    // удаление
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (!brandService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}