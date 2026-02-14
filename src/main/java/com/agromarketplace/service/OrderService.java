package com.agromarketplace.service;

import com.agromarketplace.dto.OrderDtos;
import com.agromarketplace.entity.*;
import com.agromarketplace.enums.MachineAvailabilityStatus;
import com.agromarketplace.enums.OrderItemType;
import com.agromarketplace.exception.BusinessException;
import com.agromarketplace.exception.NotFoundException;
import com.agromarketplace.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;
    private final LandRepository landRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, MachineRepository machineRepository,
                        LandRepository landRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.machineRepository = machineRepository;
        this.landRepository = landRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderDtos.CreateOrderRequest request, String username) {
        Order order = new Order();
        order.setBuyer(userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Buyer not found")));
        BigDecimal total = BigDecimal.ZERO;

        for (var itemReq : request.items()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setItemType(itemReq.itemType());
            item.setItemId(itemReq.itemId());
            item.setQuantity(itemReq.quantity());

            BigDecimal unitPrice = resolveAndReserve(itemReq.itemType(), itemReq.itemId(), itemReq.quantity());
            item.setUnitPrice(unitPrice);
            item.setLineTotal(unitPrice.multiply(BigDecimal.valueOf(itemReq.quantity())));
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(itemReq.quantity())));
            order.getItems().add(item);
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    private BigDecimal resolveAndReserve(OrderItemType type, Long id, Integer quantity) {
        if (type == OrderItemType.PRODUCT) {
            Product p = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
            if (p.getQuantity() < quantity) throw new BusinessException("Insufficient product stock");
            p.setQuantity(p.getQuantity() - quantity);
            productRepository.save(p);
            return p.getPrice();
        }
        if (type == OrderItemType.MACHINE) {
            Machine m = machineRepository.findById(id).orElseThrow(() -> new NotFoundException("Machine not found"));
            if (m.getSalePrice() == null) throw new BusinessException("Machine not for sale");
            if (quantity != 1) throw new BusinessException("Machine quantity must be 1");
            if (m.getAvailabilityStatus() != MachineAvailabilityStatus.AVAILABLE) throw new BusinessException("Machine unavailable");
            m.setAvailabilityStatus(MachineAvailabilityStatus.SOLD);
            machineRepository.save(m);
            return m.getSalePrice();
        }
        Land l = landRepository.findById(id).orElseThrow(() -> new NotFoundException("Land not found"));
        if (l.getSalePrice() == null) throw new BusinessException("Land not for sale");
        if (quantity != 1) throw new BusinessException("Land quantity must be 1");
        if (!l.getAvailable()) throw new BusinessException("Land unavailable");
        l.setAvailable(false);
        landRepository.save(l);
        return l.getSalePrice();
    }
}
