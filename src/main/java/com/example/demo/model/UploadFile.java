package com.example.demo.model;

import com.example.demo.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "images")
public class UploadFile extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uploadFileName;
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_username")
    @JsonIgnore
    private MarketBoard marketBoard;

    //이미지생성 생성자
    public UploadFile(String uploadFileName, String storeFileName, User user, MarketBoard findByMarketId) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.user = user;
        this.marketBoard = findByMarketId;
    }

}
