package com.agromarketplace.entity;

import com.agromarketplace.enums.MachineAvailabilityStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "machines")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 2000)
    private String description;

    private BigDecimal salePrice;
    private BigDecimal rentalPricePerDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MachineAvailabilityStatus availabilityStatus = MachineAvailabilityStatus.AVAILABLE;

    @ManyToOne(optional = false)
    private User owner;

    @Column(nullable = false)
    private String location;

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public java.math.BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(java.math.BigDecimal salePrice) { this.salePrice = salePrice; }
    public java.math.BigDecimal getRentalPricePerDay() { return rentalPricePerDay; }
    public void setRentalPricePerDay(java.math.BigDecimal rentalPricePerDay) { this.rentalPricePerDay = rentalPricePerDay; }
    public MachineAvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(MachineAvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
