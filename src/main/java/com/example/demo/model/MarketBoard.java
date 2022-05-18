package com.example.demo.model;


import com.example.demo.dto.request.MarketPostDto;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MarketBoard extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private int price;


    @OneToMany(mappedBy = "marketBoard", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles = new ArrayList<UploadFile>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MarketBoard(MarketPostDto postDto, User writer) {
        this.itemName = postDto.getItemName();
        this.body = postDto.getItemBody();
        this.price = postDto.getPrice();
        this.user = writer;}
}
