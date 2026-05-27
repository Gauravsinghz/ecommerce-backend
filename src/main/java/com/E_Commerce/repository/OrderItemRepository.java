package com.E_Commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
            List<OrderItem>findByOrder_Id(Long orderId);

}