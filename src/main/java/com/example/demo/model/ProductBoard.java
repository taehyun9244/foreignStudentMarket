package com.example.demo.model;

import com.example.demo.util.Timesteamed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductBoard extends Timesteamed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    public ProductBoard(String title, String contents, int price, String location) {
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.location = location;
    }
}
