package com.example.demo.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
@Getter
@RequiredArgsConstructor
public enum CountryEnum {

    USA("USA", "The United States", "미국"),
    JP("JP", "Japan", "일본"),
    CHA("CHA","China", "중국" ),
    FR("FR", "France", "프랑스"),
    GRM("GRM", "Germany", "독일"),
    THA("THA","Taiwan", "타이완" ),
    VEN("VEN", "Vietnam", "베트남"),
    SWE("SWE", "Sweden", "스웨덴"),
    TAI("THA", "Thailand", "태국");

    private final String code;
    private final String name;
    private final String nameKr;

//    static public boolean existCountry(String code) {
//        return Arrays.stream(CountryEnum.values()).filter(it -> it.code.equals(code)).count() == 1;
//    }

}
