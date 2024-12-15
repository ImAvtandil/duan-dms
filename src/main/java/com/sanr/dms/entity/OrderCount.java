package com.sanr.dms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_count")
public class OrderCount {

    @Id
    private Long id;

    @Column(name = "total_orders", nullable = false)
    private Integer totalOrders;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }
}
