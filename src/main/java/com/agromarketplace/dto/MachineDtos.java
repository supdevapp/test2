package com.agromarketplace.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MachineDtos {
    public record CreateMachineRequest(
            @NotBlank String name,
            @NotBlank String type,
            @NotBlank String description,
            BigDecimal salePrice,
            BigDecimal rentalPricePerDay,
            @NotBlank String location
    ) {}

    public record RentMachineRequest(
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate
    ) {}
}
