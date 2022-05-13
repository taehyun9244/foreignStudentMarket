package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import com.example.demo.util.CountryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DeliveryBoardSimResDto {
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

    public DeliveryBoardSimResDto(DeliveryBoard deliveryBoard) {
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.from_city = deliveryBoard.getFrom_city();
        this.from_country = deliveryBoard.getFrom_country();
        this.countComment = deliveryBoard.getCount_comment();
        this.price = deliveryBoard.getPrice();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.username = deliveryBoard.getUser().getUsername();
    }
}


