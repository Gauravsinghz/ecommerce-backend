package com.E_Commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.E_Commerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

      @Query("""
        SELECT
        COALESCE(
        SUM(o.totalPrice),
        0)

        FROM Order o

        WHERE
        o.status<>'CANCELLED'

        """)

    Double revenue();

}