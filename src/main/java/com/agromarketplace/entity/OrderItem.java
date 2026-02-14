package com.agromarketplace.entity;

import com.agromarketplace.enums.OrderItemType;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderItemType itemType;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal lineTotal;

    public void setOrder(Order order) { this.order = order; }
    public void setItemType(OrderItemType itemType) { this.itemType = itemType; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}
