package com.agromarketplace.controller;

import com.agromarketplace.entity.Rental;
import com.agromarketplace.service.RentalService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/my")
    public List<Rental> myRentals(Authentication authentication) {
        return rentalService.myRentals(authentication.getName());
    }
}
