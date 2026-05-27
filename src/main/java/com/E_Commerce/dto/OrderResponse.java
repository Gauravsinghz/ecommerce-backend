package com.E_Commerce.dto;

import com.E_Commerce.entity.OrderStatus;

public class OrderResponse {

    private Long id;

    private String userName;

    private Double totalPrice;

    private OrderStatus status;

    private Double tax;

    private Double shipping;

    private Double finalAmount;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(
            String userName) {

        this.userName = userName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(
            Double totalPrice) {

        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {

        this.status = status;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(
            Double tax) {

        this.tax = tax;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(
            Double shipping) {

        this.shipping = shipping;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(
            Double finalAmount) {

        this.finalAmount = finalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(
            String address) {

        this.address = address;
    }
}