package com.example.demo.model;

import com.example.demo.dto.request.DeliveryReq;
import com.example.demo.util.DeliveryStatus;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Delivery extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private String deliveryStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payId")
    private Pay pay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    public Delivery(Pay pay, DeliveryReq deliveryReq, User payer) {
        this.itemName = deliveryReq.getItemName();
        this.pay = pay;
        this.user = payer;
        this.address = payer.getAddress();
        this.deliveryStatus = deliveryReq.getDeliveryStatus();
    }
}
