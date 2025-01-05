package com.sanr.dms.repository;

import com.sanr.dms.entity.OrderCount;

import jakarta.persistence.LockModeType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface OrderCountRepository extends JpaRepository<OrderCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT oc FROM OrderCount oc WHERE oc.id=:id")
    Optional<OrderCount> findByIdForUpdate(Long id);
}

