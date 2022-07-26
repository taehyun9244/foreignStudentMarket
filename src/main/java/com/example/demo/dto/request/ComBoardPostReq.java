package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ComBoardPostReq {
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "소제목을 선택해 주세요")
    private String subtitle;
    @NotBlank(message = "내용을 입력해 주세요")
    private String contents;
    @NotBlank(message = "지역을 입력해 주세요")
    private String location;

}
