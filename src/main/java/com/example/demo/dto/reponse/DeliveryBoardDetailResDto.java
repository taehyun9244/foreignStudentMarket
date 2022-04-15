package com.example.demo.dto.reponse;

import com.example.demo.model.DeliveryBoard;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
