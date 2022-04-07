package com.example.demo.dto.request;

import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class DeliveryBoardPostReqDto {
    private String username;
    private Long id;
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "내용 입력해 주세요")
    private String contents;
    @NotBlank(message = "국가를 설정해 주세요")
    private String country;
    @NotBlank(message = "가격을 입력해 주세요")
    private int price;
    @NotBlank(message = "운송위치를 입력해 주세요")
    private String location;

    public DeliveryBoard toEntity(User user){
        return DeliveryBoard.builder()
                .user(user)
                .title(title)
                .contents(contents)
                .country(country)
                .price(price)
                .location(location)
                .build();
    }
}
