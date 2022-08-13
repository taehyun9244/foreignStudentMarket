package com.example.demo.model;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.util.CountryEnum;
import com.example.demo.util.Timestamped;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeliveryBoard extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int countComment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "deliveryBoard", cascade = CascadeType.REMOVE)
    private List<DeliComment> deliComments = new ArrayList<DeliComment>();

    //운송게시글 생성 생성자
    public DeliveryBoard(DeliveryBoardPostReq postReqDto, User writer){
        this.title = postReqDto.getTitle();
        this.body = postReqDto.getContents();
        this.from_city = postReqDto.getFrom_city();
        this.from_country = postReqDto.getFrom_country();
        this.to_city = writer.getAddress().getCity();
        this.price = postReqDto.getPrice();
        this.user = writer;
    }

    //운송게시글 수정 생성자
    public void editDeliveryBoard(DeliveryBoardPostReq postReqDto){
        this.title = postReqDto.getTitle();
        this.body = postReqDto.getContents();
        this.from_city = postReqDto.getFrom_city();
        this.from_country = postReqDto.getFrom_country();
        this.price = postReqDto.getPrice();
    }

    //댓글 작성시 +1
    public void addComment(int count){
         this.countComment = countComment + 1;
    }

    //댓글 삭제시 -1
    public void removeComment(int count){
        this.countComment = countComment - 1;
    }

}


