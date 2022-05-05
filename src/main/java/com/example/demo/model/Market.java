package com.example.demo.model;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Market {

    @Id @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    private String title;

    private String contents;

    private int price;


}
