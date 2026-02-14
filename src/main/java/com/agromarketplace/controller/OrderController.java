package com.agromarketplace.controller;

import com.agromarketplace.dto.OrderDtos;
import com.agromarketplace.entity.Order;
import com.agromarketplace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public Order createOrder(@Valid @RequestBody OrderDtos.CreateOrderRequest request, Authentication authentication) {
        return orderService.createOrder(request, authentication.getName());
    }
}
