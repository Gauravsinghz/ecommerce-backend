package com.E_Commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long userId);
}
