package com.E_Commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.E_Commerce.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

        Optional<User>findByEmail(String email);

}