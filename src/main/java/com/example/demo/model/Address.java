package com.example.demo.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "zipcode",nullable = false)
    private String zipcode;
}
