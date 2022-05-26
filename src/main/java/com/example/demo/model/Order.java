package com.example.demo.model;

import com.example.demo.dto.request.OrderPostReq;
import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.Timestamped;
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

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "marketId")
    private MarketBoard marketBoard;

    @Setter
    @Column(nullable = false)
    private String orderStatus;


    //주문생성 생성자
    public Order(User writer, MarketBoard orderItem, OrderPostReq postReq) {
        this.user = writer;
        this.marketBoard = orderItem;
        this.orderStatus = postReq.getOrderStatus();
        this.itemName = orderItem.getItemName();
    }
}
