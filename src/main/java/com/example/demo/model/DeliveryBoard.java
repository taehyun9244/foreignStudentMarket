package com.example.demo.model;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.util.Timesteamed;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DeliveryBoard")
public class DeliveryBoard extends Timesteamed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Long id;

    @Column(name = "DELIVERY_TITLE", nullable = false)
    private String title;

    @Column(name = "DELIVERY_CONTENTS", nullable = false)
    private String contents;

    @Column(name = "COUNTRY", nullable = false)
    private String send_country;

    @Column(name = "ADDRESS", nullable = false)
    private String send_address;

    @Column(name = "DELIVERED_COUNTRY", nullable = false)
    private String delivered_country;

    @Column(name = "DELIVERED_ADDRESS",nullable = false)
    private String delivered_address;

    @Column(name = "DELIVERY_PRICE",nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public DeliveryBoard(DeliveryBoardPostReqDto postReqDto, User findUser){
        this.id = postReqDto.getId();
        this.title = postReqDto.getTitle();
        this.contents = postReqDto.getContents();
        this.send_country = postReqDto.getSend_country();
        this.send_address = postReqDto.getSend_address();
        this.delivered_country = postReqDto.getDelivered_country();
        this.delivered_address = postReqDto.getDelivered_address();
        this.price = postReqDto.getPrice();
        this.user = findUser;
    }

    public void editDeliveryBoard(DeliveryBoardDetailResDto detailResDto){
        this.id = detailResDto.getId();
        this.title = detailResDto.getTitle();
        this.contents = detailResDto.getContents();
        this.send_country = detailResDto.getSend_country();
        this.send_address = detailResDto.getSend_address();
        this.delivered_country = detailResDto.getDelivered_country();
        this.delivered_address = detailResDto.getDelivered_address();
        this.price = detailResDto.getPrice();
    }
}
