package com.E_Commerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String code;

    private Double discount;

    private Double minimumAmount;

    public Coupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(
            String code) {

        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(
            Double discount) {

        this.discount = discount;
    }

    public Double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(
            Double minimumAmount) {

        this.minimumAmount = minimumAmount;
    }

}