package com.example.demo.model;

import com.example.demo.dto.request.OrderReq;
import com.example.demo.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_table")
public class Order extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int itemPrice;
    @Setter
    @Column(nullable = false)
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToOne
    @JoinColumn(name = "marketId")
    @JsonIgnore
    private MarketBoard marketBoard;

    //주문생성 생성자
    public Order(User writer, MarketBoard orderItem, OrderReq postReq) {
        this.user = writer;
        this.marketBoard = orderItem;
        this.orderStatus = postReq.getOrderStatus();
        this.itemName = orderItem.getItemName();
        this.itemPrice = orderItem.getPrice();
    }
}
