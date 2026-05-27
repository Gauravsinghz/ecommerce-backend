package com.E_Commerce.dto.request;

import jakarta.validation.constraints.*;

public class ProductRequest {
    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @Min(1)
    private Integer quantity;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
