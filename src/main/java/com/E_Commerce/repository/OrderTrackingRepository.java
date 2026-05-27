package com.E_Commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.OrderTracking;

public interface OrderTrackingRepository
        extends JpaRepository<OrderTracking, Long> {

    List<OrderTracking>

            findByOrder_IdOrderByUpdatedAtDesc(
                    Long orderId);

}