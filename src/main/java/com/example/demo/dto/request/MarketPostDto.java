package com.example.demo.dto.request;

import javax.validation.constraints.NotBlank;

public class MarketPostDto {
    @NotBlank(message = "상품의 이름을 입력해 주세요")
    private String title;
    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;
    @NotBlank(message = "카테고리를 설정해 주세요")
    private String category;
    @NotBlank(message = "거래하고 싶은 지역을 입력해 주세요")
    private String location;
    @NotBlank(message = "가격을 입력해 주세요")
    private int price;


}
