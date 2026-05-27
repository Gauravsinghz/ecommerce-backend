package com.E_Commerce.contoller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.E_Commerce.dto.request.WishlistRequest;
import com.E_Commerce.dto.response.WishlistResponse;

import com.E_Commerce.service.WishlistService;

@RestController
@RequestMapping(
        "/api/wishlist")

public class WishlistController {

    private WishlistService service;

    public WishlistController(
            WishlistService service) {

        this.service =
                service;

    }

    @PostMapping
    public WishlistResponse add(

            @RequestBody
            WishlistRequest request) {

        return service.add(
                request);

    }

    @GetMapping(
            "/{userId}")

    public List<WishlistResponse> get(

            @PathVariable
            Long userId) {

        return service.get(
                userId);
    }

    @PostMapping("/move/{id}")
    public String move(@PathVariable Long id) {
        return service.moveToCart( id);
    }

}