package com.E_Commerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.Wishlist;

public interface WishlistRepository
        extends JpaRepository<Wishlist, Long> {

    List<Wishlist>findByUser_Id(Long userId);
    Optional<Wishlist>findByUser_IdAndProduct_Id(Long userId,Long productId);

}