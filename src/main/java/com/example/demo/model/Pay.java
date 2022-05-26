package com.example.demo.model;

import com.example.demo.dto.request.PayReq;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Pay extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int payment;

    @Column(nullable = false)
    private String payStatus;

    @Column(nullable = false)
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID")
    private Order order;

    public Pay(User payer, Order orderItem, PayReq payReq) {
        this.user = payer;
        this.payment = payReq.getPayment();
        this.itemName = payReq.getItemName();
        this.order = orderItem;
        this.payStatus = payReq.getPayStatus();
    }
}
