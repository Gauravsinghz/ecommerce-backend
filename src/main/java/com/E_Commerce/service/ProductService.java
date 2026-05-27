package com.E_Commerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.ProductRequest;
import com.E_Commerce.dto.response.ProductPageResponse;
import com.E_Commerce.dto.response.ProductResponse;
import com.E_Commerce.entity.Product;
import com.E_Commerce.exception.ProductAlreadyExistsException;
import com.E_Commerce.exception.ProductNotFoundException;
import com.E_Commerce.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse create(ProductRequest request) {
        boolean exists = repository.findByName(request.getName()).isPresent();
        if (exists) {
            throw new ProductAlreadyExistsException("Product already exists");

        }
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        Product saved = repository.save(product);
        ProductResponse response = new ProductResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setPrice(saved.getPrice());
        response.setQuantity(saved.getQuantity());
        return response;
    }

    public List<ProductResponse> getAll() {
        return repository.findAll().stream().map(p -> {
            ProductResponse r = new ProductResponse();
            r.setId(p.getId());
            r.setName(p.getName());
            r.setPrice(p.getPrice());
            r.setQuantity(p.getQuantity());
            return r;
        }).collect(Collectors.toList());
    }

    public ProductResponse getById(Long id) {
        Product p = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        ProductResponse r = new ProductResponse();
        r.setId(p.getId());
        r.setName(p.getName());
        r.setPrice(p.getPrice());
        r.setQuantity(p.getQuantity());
        return r;
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        Product saved = repository.save(product);
        ProductResponse r = new ProductResponse();
        r.setId(saved.getId());
        r.setName(saved.getName());
        r.setPrice(saved.getPrice());
        r.setQuantity(saved.getQuantity());
        return r;
    }

    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ProductNotFoundException("Product not found with id: " + id);
        repository.deleteById(id);
    }

    public ProductPageResponse getProducts(int page, int size, String search, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Product> products;
        if (search != null && !search.isBlank()) {
            products = repository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            products = repository.findAll(pageable);
        }
        List<ProductResponse> content = products.map(product -> {
            ProductResponse pr = new ProductResponse();
            pr.setId(product.getId());
            pr.setName(product.getName());
            pr.setPrice(product.getPrice());
            pr.setQuantity(product.getQuantity());
            return pr;
        }).toList();
        ProductPageResponse response = new ProductPageResponse();
        response.setProducts(content);
        response.setPage(products.getNumber());
        response.setTotalPages(products.getTotalPages());
        response.setTotalProducts(products.getTotalElements());
        return response;
    }
}
