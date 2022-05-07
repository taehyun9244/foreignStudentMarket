package com.example.demo.model;

import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.util.DeliveryCountry;
import com.example.demo.util.Timestamped;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DeliveryBoard extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String delivered_city;

    @Column(nullable = false)
    private String delivered_street;

    @Column(name = "DELIVERY_PRICE", nullable = false)
    private int price;

    @Column(nullable = false)
    private String send_address;

    @Enumerated(EnumType.STRING)
    private DeliveryCountry send_country;

    @Column(nullable = false)
    private int count_comment;

    @Column( nullable = false)
    private int delivery_like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "deliveryBoard", cascade = CascadeType.REMOVE)
    private List<DeliComment> deliComment = new ArrayList<DeliComment>();

    //게시글 작성 데이터 가공
    public DeliveryBoard(DeliveryBoardPostReqDto postReqDto, User findUser){
        this.title = postReqDto.getTitle();
        this.contents = postReqDto.getContents();
        this.send_address = postReqDto.getSend_address();
        this.send_country = postReqDto.getCountry();
        this.delivered_city = findUser.getAddress().getCity();
        this.delivered_street = findUser.getAddress().getStreet();
        this.price = postReqDto.getPrice();
        this.user = findUser;
    }

    //게시글 수정 데이터 가공
    public void editDeliveryBoard(DeliveryBoardPostReqDto postReqDto){
        this.title = postReqDto.getTitle();
        this.contents = postReqDto.getContents();
        this.send_address = postReqDto.getSend_address();
        this.send_country = postReqDto.getCountry();
        this.price = postReqDto.getPrice();
    }

    //댓글 작성시 +1
    public void addComment(int count){
         this.count_comment = count_comment + 1;
    }

    //댓글 삭제시 -1
    public void removeComment(int count){
        this.count_comment = count_comment - 1;
    }
}
