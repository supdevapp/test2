package com.agromarketplace.repository;

import com.agromarketplace.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByRenterUsername(String username);
}
