package com.example.cosmetic_store.controller;

import com.example.cosmetic_store.model.Brand;
import com.example.cosmetic_store.model.Category;
import com.example.cosmetic_store.model.Product;
import com.example.cosmetic_store.service.BrandService;
import com.example.cosmetic_store.service.CategoryService;
import com.example.cosmetic_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.findAll();
        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);

        return "index";
    }

    // сохранение продукта
    @PostMapping("/products/save")
    public String saveProduct(@RequestParam String name,
                              @RequestParam(required = false) String description,
                              @RequestParam BigDecimal price,
                              @RequestParam Integer quantity,
                              @RequestParam Long brandId,
                              @RequestParam Long categoryId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);

        Brand brand = brandService.findById(brandId).orElse(null);
        Category category = categoryService.findById(categoryId).orElse(null);
        product.setBrand(brand);
        product.setCategory(category);

        productService.save(product);
        return "redirect:/";
    }
    // редактирование продукта
    @PostMapping("/products/update")
    public String updateProduct(@RequestParam Long id,
                                @RequestParam String name,
                                @RequestParam(required = false) String description,
                                @RequestParam BigDecimal price,
                                @RequestParam Integer quantity,
                                @RequestParam Long brandId,
                                @RequestParam Long categoryId) {
        Product product = productService.findById(id).orElse(new Product());
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantity(quantity);

        Brand brand = brandService.findById(brandId).orElse(null);
        Category category = categoryService.findById(categoryId).orElse(null);
        product.setBrand(brand);
        product.setCategory(category);

        productService.save(product);
        return "redirect:/";
    }

    // удаление продукта
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/";
    }

    // сохранение бренда
    @PostMapping("/brands/save")
    public String saveBrand(@RequestParam String name,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String country) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(description);
        brand.setCountry(country);
        brandService.save(brand);
        return "redirect:/";
    }

    // редактирование бренда
    @PostMapping("/brands/update")
    public String updateBrand(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String country) {
        Brand brand = brandService.findById(id).orElse(new Brand());
        brand.setName(name);
        brand.setDescription(description);
        brand.setCountry(country);
        brandService.save(brand);
        return "redirect:/";
    }

    // удаление бренда
    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable Long id) {
        brandService.deleteById(id);
        return "redirect:/";
    }

    // сохранение категории
    @PostMapping("/categories/save")
    public String saveCategory(@RequestParam String name,
                               @RequestParam(required = false) String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
        return "redirect:/";
    }

    // редактирование категории
    @PostMapping("/categories/update")
    public String updateCategory(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam(required = false) String description) {
        Category category = categoryService.findById(id).orElse(new Category());
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
        return "redirect:/";
    }

    // удаление категории
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/";
    }
}