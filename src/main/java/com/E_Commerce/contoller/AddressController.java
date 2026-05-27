package com.E_Commerce.contoller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_Commerce.dto.request.AddressRequest;
import com.E_Commerce.dto.response.AddressResponse;
import com.E_Commerce.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping
    public AddressResponse add(@RequestBody AddressRequest request) {
        return service.add(request);
    }

    @GetMapping("/user/{userId}")
    public List<AddressResponse> getByUser(@PathVariable Long userId) {
        return service.getByUser(userId);
    }
}
