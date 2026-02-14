package com.agromarketplace.dto;

import com.agromarketplace.enums.OrderItemType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class OrderDtos {
    public record OrderItemRequest(
            @NotNull OrderItemType itemType,
            @NotNull Long itemId,
            @NotNull @Min(1) Integer quantity
    ) {}

    public record CreateOrderRequest(
            @NotEmpty List<@Valid OrderItemRequest> items
    ) {}
}
