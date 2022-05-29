package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class DeliveryReq {

    private Long payId;
    private String itemName;
    private String deliveryStatus;
}
