package com.example.demo.model;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Market {

    @Id @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    private String title;

    private String contents;

    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
