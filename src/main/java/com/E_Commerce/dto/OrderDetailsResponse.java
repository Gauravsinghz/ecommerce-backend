package com.E_Commerce.dto;

import java.util.List;

public class OrderDetailsResponse {

    private Long orderId;

    private String user;

    private Double total;

    private List<Item> items;

    public static class Item {

        private String product;

        private Integer quantity;

        private Double price;

        public String getProduct() {
            return product;
        }

        public void setProduct(
                String product) {

            this.product = product;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(
                Integer quantity) {

            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(
                Double price) {

            this.price = price;
        }

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(
            Long orderId) {

        this.orderId = orderId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(
            String user) {

        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(
            Double total) {

        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(
            List<Item> items) {

        this.items = items;
    }

}