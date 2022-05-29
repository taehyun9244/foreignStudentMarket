package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class CancelOrderReq {

    private Long orderId;
    private Long payId;
    private String orderStatus;
}
