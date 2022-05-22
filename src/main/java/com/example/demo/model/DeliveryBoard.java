package com.example.demo.model;

import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.util.CountryEnum;
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
    private String body;

    @Column(nullable = false)
    private String from_city;

    @Enumerated(EnumType.STRING)
    private CountryEnum from_country;

    @Column(nullable = false)
    private String to_city;

    @Column(name = "DELIVERY_PRICE", nullable = false)
    private int price;

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
    public DeliveryBoard(DeliveryBoardPostReq postReqDto, User writer){
        this.title = postReqDto.getTitle();
        this.body = postReqDto.getContents();
        this.from_city = postReqDto.getFrom_city();
        this.from_country = postReqDto.getFrom_country();
        this.to_city = writer.getAddress().getCity();
        this.price = postReqDto.getPrice();
        this.user = writer;
    }

    //게시글 수정 데이터 가공
    public DeliveryBoard editDeliveryBoard(DeliveryBoardPostReq postReqDto){
        this.title = postReqDto.getTitle();
        this.body = postReqDto.getContents();
        this.from_city = postReqDto.getFrom_city();
        this.from_country = postReqDto.getFrom_country();
        this.price = postReqDto.getPrice();
        return this;
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
