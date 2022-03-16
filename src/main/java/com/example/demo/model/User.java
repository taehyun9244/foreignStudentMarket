package com.example.demo.model;

import com.example.demo.util.Timesteamed;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // DB테이블 표시
@NoArgsConstructor // 기본생성자
public class User extends Timesteamed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    public User(String username, String password, String nickname, String birthday, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
