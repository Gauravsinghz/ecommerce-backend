package com.E_Commerce.dto.request;

public class TrackingRequest {

    private Long orderId;

    private String location;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(
            Long orderId) {

        this.orderId = orderId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(
            String location) {

        this.location = location;
    }

}
