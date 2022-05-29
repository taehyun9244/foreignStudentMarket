package com.example.demo.model;


import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MarketBoard extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "marketBoard", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles = new ArrayList<UploadFile>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    //주문게시글 생성 생성자
    public MarketBoard(MarketPostReq postDto, User writer, List<UploadFile> saveImages) {
        this.itemName = postDto.getItemName();
        this.body = postDto.getItemBody();
        this.price = postDto.getPrice();
        this.category = postDto.getCategory();
        this.location = postDto.getLocation();
        this.user = writer;
        this.imageFiles = saveImages;
    }

    //주문게시글 수정 생성자
    public MarketBoard(MarketPostReq postReq, List<UploadFile> multipartFiles) {
        this.itemName = postReq.getItemName();
        this.body = postReq.getItemBody();
        this.price = postReq.getPrice();
        this.category = postReq.getCategory();
        this.imageFiles = multipartFiles;
    }
}
