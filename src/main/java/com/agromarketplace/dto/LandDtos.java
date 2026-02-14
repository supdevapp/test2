package com.agromarketplace.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LandDtos {
    public record CreateLandRequest(
            @NotBlank String title,
            @NotNull @DecimalMin("0.1") Double area,
            @NotBlank String description,
            BigDecimal salePrice,
            BigDecimal rentalPrice,
            @NotBlank String location,
            @NotBlank String soilType,
            @NotNull Boolean waterAvailability
    ) {}

    public record RentLandRequest(
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate
    ) {}
}
