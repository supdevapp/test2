package com.agromarketplace.service;

import com.agromarketplace.dto.MachineDtos;
import com.agromarketplace.entity.Machine;
import com.agromarketplace.entity.Rental;
import com.agromarketplace.enums.MachineAvailabilityStatus;
import com.agromarketplace.enums.RentalItemType;
import com.agromarketplace.exception.BusinessException;
import com.agromarketplace.exception.NotFoundException;
import com.agromarketplace.repository.MachineRepository;
import com.agromarketplace.repository.RentalRepository;
import com.agromarketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MachineService {
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public MachineService(MachineRepository machineRepository, UserRepository userRepository, RentalRepository rentalRepository) {
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Machine> getAll() { return machineRepository.findAll(); }

    public Machine create(MachineDtos.CreateMachineRequest request, String username) {
        if (request.salePrice() == null && request.rentalPricePerDay() == null) {
            throw new BusinessException("Machine must have sale or rental price");
        }
        Machine machine = new Machine();
        machine.setName(request.name());
        machine.setType(request.type());
        machine.setDescription(request.description());
        machine.setSalePrice(request.salePrice());
        machine.setRentalPricePerDay(request.rentalPricePerDay());
        machine.setLocation(request.location());
        machine.setOwner(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Owner not found")));
        return machineRepository.save(machine);
    }

    public Rental rent(Long id, MachineDtos.RentMachineRequest request, String username) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new NotFoundException("Machine not found"));
        if (machine.getRentalPricePerDay() == null) throw new BusinessException("Machine is not rentable");
        if (machine.getAvailabilityStatus() != MachineAvailabilityStatus.AVAILABLE) throw new BusinessException("Machine unavailable");

        long days = ChronoUnit.DAYS.between(request.startDate(), request.endDate()) + 1;
        if (days <= 0) throw new BusinessException("Invalid rental period");

        Rental rental = new Rental();
        rental.setRenter(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Renter not found")));
        rental.setItemType(RentalItemType.MACHINE);
        rental.setItemId(machine.getId());
        rental.setStartDate(request.startDate());
        rental.setEndDate(request.endDate());
        rental.setTotalPrice(machine.getRentalPricePerDay().multiply(BigDecimal.valueOf(days)));

        machine.setAvailabilityStatus(MachineAvailabilityStatus.RENTED);
        machineRepository.save(machine);
        return rentalRepository.save(rental);
    }

    public Machine buy(Long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new NotFoundException("Machine not found"));
        if (machine.getSalePrice() == null) throw new BusinessException("Machine is not for sale");
        if (machine.getAvailabilityStatus() == MachineAvailabilityStatus.SOLD) throw new BusinessException("Machine already sold");
        machine.setAvailabilityStatus(MachineAvailabilityStatus.SOLD);
        return machineRepository.save(machine);
    }
}
