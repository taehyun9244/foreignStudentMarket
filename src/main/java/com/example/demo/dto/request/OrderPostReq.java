package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class OrderPostReq {
    private Long marketId;
    private String orderStatus;
}
