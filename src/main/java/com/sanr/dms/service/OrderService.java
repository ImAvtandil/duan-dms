package com.sanr.dms.service;

import com.sanr.dms.entity.Order;
import com.sanr.dms.entity.OrderCount;
import com.sanr.dms.repository.OrderRepository;
import com.sanr.dms.repository.OrderCountRepository;
import jakarta.persistence.LockModeType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCountRepository orderCountRepository;

    public OrderService(OrderRepository orderRepository, OrderCountRepository orderCountRepository) {
        this.orderRepository = orderRepository;
        this.orderCountRepository = orderCountRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createOrderWithReadCommitted(String orderName) {
        // Create a new order
        Order order = new Order();
        order.setOrderName(orderName);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Update the order count 
        Optional<OrderCount> orderCountOptional = orderCountRepository.findByIdForUpdate(1L);
        if (orderCountOptional.isPresent()) {
            OrderCount orderCount = orderCountOptional.get();
            orderCount.setTotalOrders(orderCount.getTotalOrders() + 1);
            orderCountRepository.save(orderCount);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 50)
    public void createOrderWithRepeatableRead(String orderName) {
        // Create a new order
        Order order = new Order();
        order.setOrderName(orderName);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Update the order count 
        Optional<OrderCount> orderCountOptional = orderCountRepository.findById(1L);
        if (orderCountOptional.isPresent()) {
            OrderCount orderCount = orderCountOptional.get();
            orderCount.setTotalOrders(orderCount.getTotalOrders() + 1);
            orderCountRepository.save(orderCount);
        }
    }

}
