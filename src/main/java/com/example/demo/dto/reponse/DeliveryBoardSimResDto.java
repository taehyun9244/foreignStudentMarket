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

    private String delivered_street;

    private int countComment;

    private int price;

    private String delivery_city;

    private CountryEnum send_country;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    public DeliveryBoardSimResDto(DeliveryBoard deliveryBoard) {
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.delivery_city = deliveryBoard.getDelivered_city();
        this.delivered_street = deliveryBoard.getDelivered_street();
        this.send_country = deliveryBoard.getSend_country();
        this.countComment = deliveryBoard.getCount_comment();
        this.price = deliveryBoard.getPrice();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.username = deliveryBoard.getUser().getUsername();
    }
}


