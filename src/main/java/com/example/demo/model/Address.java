package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    @Column(name = "city",nullable = false, length = 5)
    private String city;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "zipcode",nullable = false)
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
