package com.example.demo.model;

import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.OrderStatus;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "marketId")
    private MarketBoard marketBoard;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
