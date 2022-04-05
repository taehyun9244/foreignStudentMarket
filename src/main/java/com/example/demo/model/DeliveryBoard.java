package com.example.demo.model;

import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.util.Timesteamed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProductBoard")
public class DeliveryBoard extends Timesteamed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENTS", nullable = false)
    private String contents;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @Column(name = "PRICE",nullable = false)
    private int price;

    @Column(name = "LOCATION",nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private entryStatus status;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @Builder
    public DeliveryBoard(String title, String contents, String country, int price, String location, User user) {
        this.title = title;
        this.contents = contents;
        this.country = country;
        this.price = price;
        this.location = location;
        this.user = user;
    }

    public void editDeliveryBoard(DeliveryBoardDetailResDto detailResDto) {
        this.title = detailResDto.getTitle();
        this.contents = detailResDto.getContents();
        this.price = detailResDto.getPrice();
        this.location = detailResDto.getLocation();
        this.country = detailResDto.getCountry();

    }
}
