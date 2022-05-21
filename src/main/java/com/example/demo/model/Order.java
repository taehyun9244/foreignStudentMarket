package com.example.demo.model;

import com.example.demo.util.OrderStatus;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_table")
public class Order extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "marketId")
    private MarketBoard marketBoard;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(User writer, MarketBoard orderItem, OrderStatus orderStatus) {
        this.user = writer;
        this.marketBoard = orderItem;
        this.orderStatus = orderStatus;
    }
}
