package com.E_Commerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double totalPrice;
    // private String status;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Double tax;
    private Double shipping;
    private Double finalAmount;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(
            Address address) {

        this.address = address;
    }

}