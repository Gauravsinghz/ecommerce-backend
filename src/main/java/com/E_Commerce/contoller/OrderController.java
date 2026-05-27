package com.E_Commerce.contoller;

import org.springframework.web.bind.annotation.*;

import com.E_Commerce.dto.request.ApplyCouponRequest;
import com.E_Commerce.dto.request.CreateOrderRequest;
import com.E_Commerce.dto.response.OrderDetailsResponse;
import com.E_Commerce.dto.response.OrderResponse;
import com.E_Commerce.dto.request.OrderStatusRequest;
import com.E_Commerce.dto.request.PaymentRequest;
import com.E_Commerce.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public OrderDetailsResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}/status")
    public OrderResponse update(@PathVariable Long id, @RequestBody OrderStatusRequest request) {
        return service.updateStatus(id, request);
    }

    @PostMapping("/coupon")
    public OrderResponse apply(@RequestBody ApplyCouponRequest request) {
        return service.applyCoupon(request);

    }

    @PostMapping("/pay")
    public OrderResponse pay(@RequestBody PaymentRequest request) {
        return service.pay(request);

    }
}