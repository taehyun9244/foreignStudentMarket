package com.example.demo.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER("ORDER", "ORDER", "주문"),
    CANCEL("CANCEL", "CANCEL", "주문취소");

    private final String code;
    private final String name;
    private final String nameKr;

//    static public boolean orderStatus(String code) {
//        if (code.equals(ORDER)){
}
