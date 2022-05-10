package com.example.demo.dto.reponse;

import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.util.CountryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryBoardDetailResDto {
    private Long id;
    private String title;
    private String contents;
    private String delivery_city;
    private String delivered_street;
    private CountryEnum send_country;
    private String send_address;
    private int countComment;
    private int price;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private List<DeliComment> comment;

    public DeliveryBoardDetailResDto (DeliveryBoard deliveryBoard){
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.contents = deliveryBoard.getContents();
        this.delivery_city = deliveryBoard.getDelivered_city();
        this.delivered_street = deliveryBoard.getDelivered_street();
        this.send_country = deliveryBoard.getSend_country();
        this.send_address = deliveryBoard.getSend_address();
        this.countComment = deliveryBoard.getCount_comment();
        this.price = deliveryBoard.getPrice();
        this.username = deliveryBoard.getUser().getUsername();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.comment = deliveryBoard.getDeliComment();
    }
}
