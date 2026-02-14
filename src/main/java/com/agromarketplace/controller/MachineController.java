package com.agromarketplace.controller;

import com.agromarketplace.dto.MachineDtos;
import com.agromarketplace.entity.Machine;
import com.agromarketplace.entity.Rental;
import com.agromarketplace.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
public class MachineController {
    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    public List<Machine> getAll() { return machineService.getAll(); }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public Machine create(@Valid @RequestBody MachineDtos.CreateMachineRequest request, Authentication authentication) {
        return machineService.create(request, authentication.getName());
    }

    @PostMapping("/{id}/rent")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public Rental rent(@PathVariable Long id, @Valid @RequestBody MachineDtos.RentMachineRequest request,
                       Authentication authentication) {
        return machineService.rent(id, request, authentication.getName());
    }

    @PostMapping("/{id}/buy")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public Machine buy(@PathVariable Long id) {
        return machineService.buy(id);
    }
}
