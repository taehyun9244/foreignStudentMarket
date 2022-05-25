package com.example.demo.dto.request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class MarketPostReq {
    @NotBlank(message = "상품의 이름을 입력해 주세요")
    private String itemName;
    @NotBlank(message = "내용을 입력해 주세요")
    private String itemBody;
    @NotBlank(message = "카테고리를 설정해 주세요")
    private String category;
    @NotBlank(message = "배달 지역을 입력해 주세요")
    private String location;
    @NotBlank(message = "가격을 입력해 주세요")
    private int price;
}
