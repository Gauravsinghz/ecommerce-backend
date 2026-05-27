package com.E_Commerce.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_Commerce.dto.request.AddCartRequest;
import com.E_Commerce.dto.request.SelectAddressRequest;
import com.E_Commerce.dto.response.CartResponse;
import com.E_Commerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping
    public CartResponse add(@RequestBody AddCartRequest request) {
        return service.add(request);
    }

    @GetMapping("/{userId}")
    public List<CartResponse> get(@PathVariable Long userId) {
        return service.getCart(userId);
    }

    @PutMapping("/address")
    public Map<String, String> selectAddress(@RequestBody SelectAddressRequest request) {
        return service.selectAddress(request);
    }
}
