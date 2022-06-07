package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import com.example.demo.util.CountryEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryBoardSimRes {
    private Long id;
    private String title;
    private String username;
    private int countComment;
    private int price;
    private String from_city;
    private CountryEnum from_country;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    public DeliveryBoardSimRes(DeliveryBoard deliveryBoard) {
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.from_city = deliveryBoard.getFrom_city();
        this.from_country = deliveryBoard.getFrom_country();
        this.countComment = deliveryBoard.getCountComment();
        this.price = deliveryBoard.getPrice();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.username = deliveryBoard.getUser().getUsername();
    }
}


