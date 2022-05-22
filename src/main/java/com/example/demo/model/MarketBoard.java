package com.example.demo.model;


import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.util.CategoryEnum;
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
    @Column(name = "market_id")
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @OneToMany(mappedBy = "marketBoard", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles = new ArrayList<UploadFile>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;


    public MarketBoard(MarketPostReq postDto, User writer, List<UploadFile> saveImages) {
        this.itemName = postDto.getItemName();
        this.body = postDto.getItemBody();
        this.price = postDto.getPrice();
        this.category = postDto.getCategory();
        this.location = postDto.getLocation();
        this.user = writer;
        this.imageFiles = saveImages;
    }
}
