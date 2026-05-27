package com.E_Commerce.dto;

public class CreateOrderRequest {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long addressId;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(
            Long addressId) {

        this.addressId = addressId;
    }

}