package com.E_Commerce.dto.request;

public class SelectAddressRequest {

    private Long userId;
    private Long addressId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
}
