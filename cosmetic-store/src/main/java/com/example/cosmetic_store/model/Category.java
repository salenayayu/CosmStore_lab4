package com.example.cosmetic_store.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

@Entity
@Table(name = "category")
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @XmlElement
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @XmlElement
    private String description;

    // конструкторы
    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}