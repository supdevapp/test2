package com.agromarketplace.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lands")
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false, length = 2000)
    private String description;

    private BigDecimal salePrice;
    private BigDecimal rentalPrice;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String soilType;

    @Column(nullable = false)
    private Boolean waterAvailability;

    @ManyToOne(optional = false)
    private User owner;

    @Column(nullable = false)
    private Boolean available = true;

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public BigDecimal getRentalPrice() { return rentalPrice; }
    public void setRentalPrice(BigDecimal rentalPrice) { this.rentalPrice = rentalPrice; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public Boolean getWaterAvailability() { return waterAvailability; }
    public void setWaterAvailability(Boolean waterAvailability) { this.waterAvailability = waterAvailability; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
}
