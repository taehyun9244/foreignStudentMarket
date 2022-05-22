package com.example.demo.dto.request;

import com.example.demo.util.OrderStatus;
import lombok.Getter;

@Getter
public class OrderPostReq {
    private Long marketId;
    private OrderStatus orderStatus;
}
