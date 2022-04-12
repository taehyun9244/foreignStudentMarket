package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBoardDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private String send_country;
    private String send_address;
    private String delivered_country;
    private String delivered_address;
    private int price;
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    public DeliveryBoardDetailResDto (DeliveryBoard deliveryBoard){
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.contents = deliveryBoard.getContents();
        this.send_country = deliveryBoard.getSend_country();
        this.send_address = deliveryBoard.getSend_address();
        this.delivered_country = deliveryBoard.getDelivered_country();
        this.delivered_address = deliveryBoard.getDelivered_address();
        this.price = deliveryBoard.getPrice();
        this.username = deliveryBoard.getUser().getUsername();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
    }
}
