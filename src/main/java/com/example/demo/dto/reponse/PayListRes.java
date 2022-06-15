package com.example.demo.dto.reponse;

import com.example.demo.model.Address;
import com.example.demo.model.Pay;
import lombok.Getter;

@Getter
public class PayListRes {

    private Long orderId;
    private String itemName;
    private int price;
    private String payStatus;
    private Address to_address;


    public PayListRes(Pay pay) {
        this.orderId = pay.getOrder().getId();
        this.itemName = pay.getItemName();
        this.price = pay.getItemPrice();
        this.payStatus = pay.getPayStatus();
        this.to_address = pay.getUser().getAddress();
    }
}
