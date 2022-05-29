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
public class DeliveryBoardDetailRes {
    private Long id;
    private String title;
    private String contents;
    private String from_city;
    private CountryEnum from_country;
    private String to_address;
    private int countComment;
    private int price;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private List<DeliComment> comment;

    public DeliveryBoardDetailRes(DeliveryBoard deliveryBoard){
        this.id = deliveryBoard.getId();
        this.title = deliveryBoard.getTitle();
        this.contents = deliveryBoard.getBody();
        this.from_city = deliveryBoard.getFrom_city();
        this.from_country = deliveryBoard.getFrom_country();
        this.to_address = deliveryBoard.getTo_city();
        this.countComment = deliveryBoard.getCountComment();
        this.price = deliveryBoard.getPrice();
        this.username = deliveryBoard.getUser().getUsername();
        this.createdAt = deliveryBoard.getCreatedAt();
        this.updateAt = deliveryBoard.getUpdateAt();
        this.comment = deliveryBoard.getDeliComment();
    }
}
