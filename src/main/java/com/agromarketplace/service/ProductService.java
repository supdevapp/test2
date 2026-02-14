package com.agromarketplace.service;

import com.agromarketplace.dto.ProductDtos;
import com.agromarketplace.entity.Category;
import com.agromarketplace.entity.Product;
import com.agromarketplace.exception.NotFoundException;
import com.agromarketplace.repository.CategoryRepository;
import com.agromarketplace.repository.ProductRepository;
import com.agromarketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product create(ProductDtos.CreateProductRequest request, String username) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setCategory(resolveCategory(request.category()));
        product.setImages(request.images() == null ? List.of() : request.images());
        product.setSeller(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Seller not found")));
        return productRepository.save(product);
    }

    private Category resolveCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(categoryName);
                    return categoryRepository.save(category);
                });
    }
}
