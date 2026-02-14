package com.agromarketplace.controller;

import com.agromarketplace.dto.ProductDtos;
import com.agromarketplace.entity.Product;
import com.agromarketplace.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public Product create(@Valid @RequestBody ProductDtos.CreateProductRequest request, Authentication authentication) {
        return productService.create(request, authentication.getName());
    }
}
