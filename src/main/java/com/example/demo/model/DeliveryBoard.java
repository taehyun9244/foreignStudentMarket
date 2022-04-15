package com.example.demo.model;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.util.Timestamped;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DeliveryBoard")
public class DeliveryBoard extends Timestamped {

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

    @Column(name = "DELIVERED_ADDRESS", nullable = false)
    private String delivered_address;

    @Column(name = "DELIVERY_PRICE", nullable = false)
    private int price;

    @Column(name = "COUNT_COMMENT", nullable = false)
    private int countComment;

    @Column(name = "LIKE_DELIBOARD", nullable = false)
    private int delivery_like;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    //게시글 작성 데이터 가공
    public DeliveryBoard(DeliveryBoardPostReqDto postReqDto, User findUser){
        this.title = postReqDto.getTitle();
        this.contents = postReqDto.getContents();
        this.send_country = postReqDto.getSend_country();
        this.send_address = postReqDto.getSend_address();
        this.delivered_country = postReqDto.getDelivered_country();
        this.delivered_address = postReqDto.getDelivered_address();
        this.price = postReqDto.getPrice();
        this.user = findUser;
    }

    //게시글 수정 데이터 가공
    public void editDeliveryBoard(DeliveryBoardPostReqDto postReqDto){
        this.title = postReqDto.getTitle();
        this.contents = postReqDto.getContents();
        this.send_country = postReqDto.getSend_country();
        this.send_address = postReqDto.getSend_address();
        this.delivered_country = postReqDto.getDelivered_country();
        this.delivered_address = postReqDto.getDelivered_address();
        this.price = postReqDto.getPrice();
    }
}
