package com.example.demo.dto.request;

import com.example.demo.util.CountryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryBoardPostReqDto {
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "내용 입력해 주세요")
    private String contents;
    @NotBlank(message = "운송료를 입력해 주세요")
    private int price;
    @NotBlank(message = "배송국가의 지역을 설정해 주세요")
    private String send_address;
    @NotBlank(message = "배송국가를 설정해 주세요")
    private CountryEnum country;

}
