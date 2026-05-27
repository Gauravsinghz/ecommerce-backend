package com.E_Commerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String city;

    private String state;

    private String country;

    private String zip;

    private String line1;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(
            String city) {

        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(
            String state) {

        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(
            String country) {

        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(
            String zip) {

        this.zip = zip;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(
            String line1) {

        this.line1 = line1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(
            User user) {

        this.user = user;
    }

}