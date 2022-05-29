package com.example.demo.model;

import com.example.demo.dto.request.PayReq;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Pay extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private int itemPrice;
    @Setter
    @Column(nullable = false)
    private String payStatus;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID")
    private Order order;

    public Pay(User payer, Order orderItem, PayReq payReq) {
        this.user = payer;
        this.itemName = payReq.getItemName();
        this.itemPrice = payReq.getItemPrice();
        this.order = orderItem;
        this.payStatus = payReq.getPayStatus();
    }

    public Pay(User payer, Order orderItem, String payStatus) {
        this.user = payer;
        this.order = orderItem;
        this.itemName = orderItem.getItemName();
        this.itemPrice = orderItem.getItemPrice();
        this.payStatus = payStatus;
    }
}
