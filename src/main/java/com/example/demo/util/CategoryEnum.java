package com.example.demo.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryEnum {
    ET("ET", "electronic devices", "전자기기"),
    FN("FN", "furniture","가구"),
    BK("BK", "Books","책"),
    TK("TK", "Tickets","티켓"),
    CT("CT", "Clothes", "옷");
    private final String code;
    private final String name;
    private final String nameKr;
}
