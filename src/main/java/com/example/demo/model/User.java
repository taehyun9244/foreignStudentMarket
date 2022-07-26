package com.example.demo.model;

import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // DB테이블 표시
@NoArgsConstructor // 기본생성자
@Getter
@Table(name = "users")
public class User extends Timestamped{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String birthday;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<DeliveryBoard> deliveryBoards = new ArrayList<DeliveryBoard>();

    @OneToMany(mappedBy = "user")
    private List<CommunityBoard> communityBoards = new ArrayList<CommunityBoard>();

    @OneToMany(mappedBy = "user")
    private List<DeliComment> comments = new ArrayList<DeliComment>();

    @OneToMany(mappedBy = "user")
    private List<CommunityComment> communityComments = new ArrayList<CommunityComment>();

    @OneToMany(mappedBy = "user")
    private List<MarketBoard> marketBoards = new ArrayList<MarketBoard>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(mappedBy = "user")
    private List<Pay> pays = new ArrayList<Pay>();

    @OneToMany(mappedBy = "user")
    private List<Delivery> deliveries = new ArrayList<Delivery>();

    @OneToMany(mappedBy = "user")
    private List<UploadFile> uploadFiles = new ArrayList<UploadFile>();

    //유저 회원가입
    public User(String username, String password, String birthday, String email, String phoneNumber, Address address) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

