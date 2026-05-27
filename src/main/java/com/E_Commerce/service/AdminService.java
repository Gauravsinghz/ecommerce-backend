package com.E_Commerce.service;

import org.springframework.stereotype.Service;

import com.E_Commerce.dto.response.DashboardResponse;

import com.E_Commerce.repository.OrderRepository;

import com.E_Commerce.repository.ProductRepository;

@Service
public class AdminService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public AdminService(

            OrderRepository orderRepository,

            ProductRepository productRepository) {

        this.orderRepository =
                orderRepository;

        this.productRepository =
                productRepository;

    }

    public DashboardResponse dashboard() {

        DashboardResponse response =
                new DashboardResponse();

        response.setTotalOrders(

                orderRepository
                        .count()

        );

        response.setTotalRevenue(

                orderRepository
                        .revenue()

        );

        response.setTotalProducts(

                productRepository
                        .count()

        );

        return response;

    }

}