package com.example.demo.model;

import com.example.demo.util.Timesteamed;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // DB테이블 표시
@NoArgsConstructor // 기본생성자
@Getter
@Table(name = "User")
public class User extends Timesteamed{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "BIRTHDAY", nullable = false)
    private String birthday;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONENUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @OneToMany(mappedBy = "user")
    private List<DeliveryBoard> boards = new ArrayList<DeliveryBoard>();

//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>(Comment);

    public User(String username, String password, String nickname, String birthday, String email, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

