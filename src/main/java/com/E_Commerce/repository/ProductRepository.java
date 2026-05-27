package com.E_Commerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.Product;

    public interface ProductRepository extends JpaRepository<Product,Long> {

        Optional<Product> findByName(String name);

        Page<Product>findByNameContainingIgnoreCase(String name,Pageable pageable);

}


