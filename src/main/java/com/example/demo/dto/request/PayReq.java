package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class PayReq {

    private Long orderId;
    private String itemName;
    private String payStatus;
    private int itemPrice;
}
