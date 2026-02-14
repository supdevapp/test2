package com.agromarketplace.service;

import com.agromarketplace.dto.LandDtos;
import com.agromarketplace.entity.Land;
import com.agromarketplace.entity.Rental;
import com.agromarketplace.enums.RentalItemType;
import com.agromarketplace.exception.BusinessException;
import com.agromarketplace.exception.NotFoundException;
import com.agromarketplace.repository.LandRepository;
import com.agromarketplace.repository.RentalRepository;
import com.agromarketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LandService {
    private final LandRepository landRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public LandService(LandRepository landRepository, UserRepository userRepository, RentalRepository rentalRepository) {
        this.landRepository = landRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Land> getAll() { return landRepository.findAll(); }

    public Land create(LandDtos.CreateLandRequest request, String username) {
        if (request.salePrice() == null && request.rentalPrice() == null) {
            throw new BusinessException("Land must have sale or rental price");
        }
        Land land = new Land();
        land.setTitle(request.title());
        land.setArea(request.area());
        land.setDescription(request.description());
        land.setSalePrice(request.salePrice());
        land.setRentalPrice(request.rentalPrice());
        land.setLocation(request.location());
        land.setSoilType(request.soilType());
        land.setWaterAvailability(request.waterAvailability());
        land.setOwner(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Owner not found")));
        return landRepository.save(land);
    }

    public Rental rent(Long id, LandDtos.RentLandRequest request, String username) {
        Land land = landRepository.findById(id).orElseThrow(() -> new NotFoundException("Land not found"));
        if (land.getRentalPrice() == null) throw new BusinessException("Land is not rentable");
        if (!land.getAvailable()) throw new BusinessException("Land unavailable");

        long days = ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;
        if (days <= 0) throw new BusinessException("Invalid rental period");

        Rental rental = new Rental();
        rental.setRenter(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Renter not found")));
        rental.setItemType(RentalItemType.LAND);
        rental.setItemId(land.getId());
        rental.setStartDate(request.startDate());
        rental.setEndDate(request.endDate());
        rental.setTotalPrice(land.getRentalPrice().multiply(BigDecimal.valueOf(days)));

        land.setAvailable(false);
        landRepository.save(land);
        return rentalRepository.save(rental);
    }

    public Land buy(Long id) {
        Land land = landRepository.findById(id).orElseThrow(() -> new NotFoundException("Land not found"));
        if (land.getSalePrice() == null) throw new BusinessException("Land is not for sale");
        if (!land.getAvailable()) throw new BusinessException("Land unavailable");
        land.setAvailable(false);
        return landRepository.save(land);
    }
}
