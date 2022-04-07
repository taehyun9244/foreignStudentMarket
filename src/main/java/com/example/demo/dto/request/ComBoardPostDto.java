package com.example.demo.dto.request;

import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ComBoardPostDto {
    private String username;
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

    public  CommunityBoard postComBoard(User username){
        return CommunityBoard.builder()
                .user(username)
                .com_title(com_title)
                .com_subtitle(com_subtitle)
                .com_location(com_location)
                .com_country(com_country)
                .comm_contents(com_contents)
                .build();
    }

}
