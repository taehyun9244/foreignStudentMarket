package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class CancelPayReq {

    private String payStatus;
    private Long deliveryId;
    private Long payId;
    private Long orderId;
}
