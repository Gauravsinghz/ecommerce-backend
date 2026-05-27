package com.E_Commerce.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.AddCartRequest;
import com.E_Commerce.dto.request.SelectAddressRequest;
import com.E_Commerce.dto.response.CartResponse;
import com.E_Commerce.entity.Address;
import com.E_Commerce.entity.Cart;
import com.E_Commerce.entity.Product;
import com.E_Commerce.entity.User;
import com.E_Commerce.exception.ProductNotFoundException;
import com.E_Commerce.exception.UserNotFoundException;
import com.E_Commerce.repository.AddressRepository;
import com.E_Commerce.repository.CartRepository;
import com.E_Commerce.repository.ProductRepository;
import com.E_Commerce.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    public CartService(CartRepository repository, UserRepository userRepository,
            ProductRepository productRepository, AddressRepository addressRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }

    public CartResponse add(AddCartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());
        Cart saved = repository.save(cart);
        return toResponse(saved);
    }

    public List<CartResponse> getCart(Long userId) {
        return repository.findByUser_Id(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Map<String, String> selectAddress(SelectAddressRequest request) {
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
        List<Cart> cartItems = repository.findByUser_Id(request.getUserId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        cartItems.forEach(item -> item.setSelectedAddress(address));
        repository.saveAll(cartItems);
        return Map.of("message", "Address selected");
    }

    private CartResponse toResponse(Cart c) {
        CartResponse r = new CartResponse();
        r.setId(c.getId());
        r.setUserName(c.getUser().getFullName());
        r.setProductName(c.getProduct().getName());
        r.setProductPrice(c.getProduct().getPrice());
        r.setQuantity(c.getQuantity());
        return r;
    }
}
