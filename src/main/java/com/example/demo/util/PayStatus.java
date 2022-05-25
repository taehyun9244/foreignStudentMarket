package com.example.demo.util;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayStatus {

    WFD("WFD", "Waiting for deposit", "입금 대기"),
    COMP("COMP", "Completion", "결제완료"),
    REF("REF", "Refund", "환불");

    private final String code;
    private final String name;
    private final String nameKr;
}
