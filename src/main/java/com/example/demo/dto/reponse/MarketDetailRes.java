package com.example.demo.dto.reponse;

import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketDetailRes {

    private String username;
    private String itemName;
    private String body;
    private String city;
    private String category;
    private int price;
    private LocalDateTime creatAt;
    private List<UploadFile> uploadFiles;

    public MarketDetailRes(MarketBoard marketBoard) {
        this.username = marketBoard.getUser().getUsername();
        this.itemName = marketBoard.getItemName();
        this.body = marketBoard.getBody();
        this.city = marketBoard.getUser().getAddress().getCity();
        this.price = marketBoard.getPrice();
        this.category = marketBoard.getCategory();
        this.creatAt = marketBoard.getCreatedAt();
        this.uploadFiles = marketBoard.getUploadFiles();
    }
}
