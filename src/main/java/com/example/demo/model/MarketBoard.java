package com.example.demo.model;


import com.example.demo.util.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MarketBoard extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
