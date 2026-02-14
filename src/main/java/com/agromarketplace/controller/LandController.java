package com.agromarketplace.controller;

import com.agromarketplace.dto.LandDtos;
import com.agromarketplace.entity.Land;
import com.agromarketplace.entity.Rental;
import com.agromarketplace.service.LandService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lands")
public class LandController {
    private final LandService landService;

    public LandController(LandService landService) {
        this.landService = landService;
    }

    @GetMapping
    public List<Land> getAll() { return landService.getAll(); }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public Land create(@Valid @RequestBody LandDtos.CreateLandRequest request, Authentication authentication) {
        return landService.create(request, authentication.getName());
    }

    @PostMapping("/{id}/rent")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public Rental rent(@PathVariable Long id, @Valid @RequestBody LandDtos.RentLandRequest request,
                       Authentication authentication) {
        return landService.rent(id, request, authentication.getName());
    }

    @PostMapping("/{id}/buy")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public Land buy(@PathVariable Long id) {
        return landService.buy(id);
    }
}
