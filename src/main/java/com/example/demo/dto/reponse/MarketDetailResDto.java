package com.example.demo.dto.reponse;

import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MarketDetailResDto {

    private String username;
    private String itemName;
    private String contents;
    private String location;
    private String category;
    private int price;
    private LocalDateTime creatAt;
    private List<UploadFile> uploadFiles;

    public MarketDetailResDto(MarketBoard marketBoard, List<UploadFile> findByImages) {
        this.username = marketBoard.getUser().getUsername();
        this.itemName = marketBoard.getItemName();
        this.contents = marketBoard.getBody();
        this.location = marketBoard.getUser().getAddress().getCity();
        this.price = marketBoard.getPrice();
        this.creatAt = marketBoard.getCreatedAt();
        this.uploadFiles = findByImages;
    }
}
