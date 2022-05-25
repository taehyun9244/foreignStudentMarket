package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class PayReq {

    private String itemName;
    private String payStatus;
    private int payment;
    private Long orderId;
}
