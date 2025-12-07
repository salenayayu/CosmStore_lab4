package com.example.cosmetic_store.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    @XmlElement
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @XmlElement
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @XmlElement
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    @XmlElement
    private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @XmlElement
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @XmlElement
    private Category category;

    // конструкторы
    public Product() {}

    public Product(String name, String description, BigDecimal price, Integer quantity, Brand brand, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
    }

    // геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}