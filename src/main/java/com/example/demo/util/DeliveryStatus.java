package com.example.demo.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    OA("OA", "Order reception", "주문 접수"),
    DC("DC", "Deposit confirmation", "입금확인"),
    READY("READY", "Preparing for delivery", "배송준비중"),
    SHP("SHP", "Shipping", "발송"),
    COMP("COMP", "Completion", "배송완료"),
    CANCEL("CANCEL", "Cancel", "배송취소");

    private final String code;
    private final String name;
    private final String nameKr;

    //1 주문접수 2 입금확인 3 배송준비중 4 발송 5 배송완료
}
