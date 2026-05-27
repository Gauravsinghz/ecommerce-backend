package com.E_Commerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

    List<Cart>findByUser_Id(Long userId);
    Optional<Cart>findByUser_IdAndProduct_Id(Long userId,Long productId);

    }