package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
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
    private String send_country;
    private String send_address;
    private String delivered_country;
    private String delivered_address;
    private String username;
    private int countComment;
    private int price;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    public DeliveryBoardSimResDto(DeliveryBoard deliveryBoard) {
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.send_country = deliveryBoard.getSend_country();
        this.send_address = deliveryBoard.getSend_address();
        this.delivered_country = deliveryBoard.getDelivered_country();
        this.delivered_address = deliveryBoard.getDelivered_address();
        this.countComment = deliveryBoard.getCountComment();
        this.price = deliveryBoard.getPrice();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.username = deliveryBoard.getUser().getUsername();
    }
}
