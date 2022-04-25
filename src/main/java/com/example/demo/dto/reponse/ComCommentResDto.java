package com.example.demo.dto.reponse;

import lombok.Getter;

@Getter
public class ComCommentResDto {
    private Long id;
    private String comComment;
    private String username;
    private Long communityBoardId;
    private Long userId;
}
