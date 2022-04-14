package com.example.demo.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ComBoardPostDto {
    private Long id;
    @NotBlank(message = "제목을 입력해 주세요")
    private String com_title;
    @NotBlank(message = "소제목을 선택해 주세요")
    private String com_subtitle;
    @NotBlank(message = "내용을 입력해 주세요")
    private String com_contents;
    @NotBlank(message = "지역을 입력해 주세요")
    private String com_location;
    @NotBlank(message = "국가를 입력해 주세요")
    private String com_country;
    private String username;
}
