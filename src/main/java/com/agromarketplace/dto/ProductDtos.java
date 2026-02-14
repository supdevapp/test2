package com.agromarketplace.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class ProductDtos {
    public record CreateProductRequest(
            @NotBlank String name,
            @NotBlank String description,
            @NotNull @DecimalMin("0.0") BigDecimal price,
            @NotNull @Min(1) Integer quantity,
            @NotBlank String category,
            List<String> images
    ) {}
}
