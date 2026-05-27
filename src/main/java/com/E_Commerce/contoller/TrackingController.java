package com.E_Commerce.contoller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_Commerce.dto.request.TrackingRequest;
import com.E_Commerce.entity.OrderTracking;
import com.E_Commerce.service.TrackingService;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @PostMapping
    public OrderTracking add(@RequestBody TrackingRequest request) {

        return service.add(
                request);

    }

    @GetMapping("/{orderId}")

    public List<OrderTracking> get(

            @PathVariable

            Long orderId) {

        return service.get(
                orderId);

    }

}
