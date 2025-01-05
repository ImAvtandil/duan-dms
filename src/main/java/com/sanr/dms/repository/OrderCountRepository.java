package com.sanr.dms.repository;

import com.sanr.dms.entity.OrderCount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCountRepository extends JpaRepository<OrderCount, Long> {
}

