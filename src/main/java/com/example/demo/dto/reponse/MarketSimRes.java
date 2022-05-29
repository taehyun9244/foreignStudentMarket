package com.example.demo.dto.reponse;

import com.example.demo.model.MarketBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MarketSimRes {

    private String username;
    private String location;
    private String itemName;
    private int price;
    private LocalDateTime creatAt;

    public MarketSimRes(MarketBoard marketBoard) {
        this.username = marketBoard.getUser().getUsername();
        this.location = marketBoard.getUser().getAddress().getCity();
        this.itemName = marketBoard.getItemName();
        this.price = marketBoard.getPrice();
        this.creatAt = marketBoard.getCreatedAt();
    }
}
