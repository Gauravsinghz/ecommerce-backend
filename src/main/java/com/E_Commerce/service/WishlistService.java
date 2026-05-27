package com.E_Commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.WishlistRequest;
import com.E_Commerce.dto.response.WishlistResponse;
import com.E_Commerce.entity.*;

import com.E_Commerce.exception.AlreadyInWishlistException;
import com.E_Commerce.exception.ProductNotFoundException;
import com.E_Commerce.exception.UserNotFoundException;
import com.E_Commerce.exception.WishlistItemNotFoundException;
import com.E_Commerce.repository.*;

@Service
public class WishlistService {

        private WishlistRepository repository;

        private UserRepository userRepository;

        private ProductRepository productRepository;

        private CartRepository cartRepository;

        public WishlistService(WishlistRepository repository, UserRepository userRepository,
                        ProductRepository productRepository, CartRepository cartRepository) {

                this.repository = repository;

                this.userRepository = userRepository;

                this.productRepository = productRepository;

                this.cartRepository = cartRepository;

        }

        public WishlistResponse add(WishlistRequest request) {
                boolean exists = repository.findByUser_IdAndProduct_Id(
                                request.getUserId(), request.getProductId()).isPresent();
                if (exists) {
                        throw new AlreadyInWishlistException("Already in wishlist");
                }
                User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
                Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
                Wishlist saved = new Wishlist();
                saved.setUser(user);
                saved.setProduct(product);
                repository.save(saved);
                WishlistResponse response = new WishlistResponse();
                response.setId(saved.getId());
                response.setProductId(product.getId());
                response.setProductName(product.getName());
                response.setPrice(product.getPrice());
                return response;
        }

        public List<WishlistResponse> get(Long userId) {
                List<Wishlist> items = repository.findByUser_Id(userId);
                return items.stream().map(item -> {
                        WishlistResponse response = new WishlistResponse();
                        response.setId(item.getId());
                        response.setProductId(item.getProduct().getId());
                        response.setProductName(item.getProduct().getName());
                        response.setPrice(item.getProduct().getPrice());
                        return response;
                }).toList();
        }

        public String moveToCart(Long wishlistId) {
                Wishlist wishlist = repository.findById(wishlistId)
                                .orElseThrow(() -> new WishlistItemNotFoundException("Wishlist item not found"));
                Cart cart = cartRepository
                                .findByUser_IdAndProduct_Id(wishlist.getUser().getId(), wishlist.getProduct().getId())
                                .orElse(null);
                if (cart != null) {
                        cart.setQuantity(cart.getQuantity() + 1);
                } else {
                        cart = new Cart();
                        cart.setUser(wishlist.getUser());
                        cart.setProduct(wishlist.getProduct());
                        cart.setQuantity(1);
                }
                cartRepository.save(cart);
                repository.delete(wishlist);
                return "Moved to cart";
        }

}