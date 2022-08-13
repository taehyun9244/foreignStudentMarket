package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayReq {

    private Long orderId;
    private String payStatus;
    private String itemName;
    private int itemPrice;
}
