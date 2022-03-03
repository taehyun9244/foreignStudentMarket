package com.example.demo.User;

import com.example.demo.Util.Timesteamed;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // DB테이블 역할
@NoArgsConstructor
public class User extends Timesteamed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    private String password;
    private int birthday;


    public User(Long id, String name, String password, int birthday) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
    }
}
