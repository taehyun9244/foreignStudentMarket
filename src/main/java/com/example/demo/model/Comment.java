package com.example.demo.model;

import javax.persistence.*;

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
