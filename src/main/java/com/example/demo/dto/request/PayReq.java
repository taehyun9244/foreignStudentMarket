package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class PayReq {

    private Long orderId;
    private String payStatus;
    private String itemName;
    private int itemPrice;
}
