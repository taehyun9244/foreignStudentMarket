package com.example.demo.model;

import com.example.demo.util.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payId")
    private Pay pay;

    public Delivery(User payer, Order orderItem, DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.itemName = orderItem.getItemName();
        this.address = payer.getAddress();
        this.pay =
    }

}
