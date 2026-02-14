package com.agromarketplace.service;

import com.agromarketplace.entity.Rental;
import com.agromarketplace.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> myRentals(String username) {
        return rentalRepository.findByRenterUsername(username);
    }
}
