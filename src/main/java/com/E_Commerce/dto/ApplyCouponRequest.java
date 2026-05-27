package com.E_Commerce.dto;

public class ApplyCouponRequest {

    private Long orderId;

    private String coupon;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(
            Long orderId) {

        this.orderId = orderId;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(
            String coupon) {

        this.coupon = coupon;
    }

}