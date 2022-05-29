package com.example.demo.dto.request;

import com.example.demo.model.Address;
import lombok.Getter;

@Getter
public class DeliveryReq {

    private Long payId;
    private String itemName;
    private Address to_address;
    private String deliveryStatus;
}
