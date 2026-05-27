package com.E_Commerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.E_Commerce.entity.*;

import com.E_Commerce.repository.*;

@Service
public class TrackingService {

    private OrderRepository orderRepository;

    private OrderTrackingRepository repository;

    public TrackingService(

            OrderRepository orderRepository,

            OrderTrackingRepository repository) {

        this.orderRepository = orderRepository;

        this.repository = repository;

    }

    public OrderTracking add(
            com.E_Commerce.dto.request.TrackingRequest request) {

        Order order =

                orderRepository

                        .findById(
                                request.getOrderId())

                        .orElseThrow();

        OrderTracking t = new OrderTracking();

        t.setOrder(
                order);

        t.setStatus(

                order
                        .getStatus()
                        .name()

        );

        t.setLocation(
                request.getLocation());

        t.setUpdatedAt(
                LocalDateTime.now());

        return repository.save(
                t);

    }

    public List<OrderTracking> get(
            Long orderId) {

        return repository

                .findByOrder_IdOrderByUpdatedAtDesc(
                        orderId);

    }

}
