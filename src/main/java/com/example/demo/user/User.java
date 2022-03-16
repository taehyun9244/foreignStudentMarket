package com.example.demo.user;

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
    private String birthday;


    public User(String username, String password, String birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }
}
