package com.example.demo.model;

import com.example.demo.util.OrderStatus;
import com.example.demo.util.Timestamped;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Order extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
