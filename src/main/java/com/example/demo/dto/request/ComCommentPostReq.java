package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class ComCommentPostReq {
    private String comComment;
    private Long communityBoardId;
    private Long userId;
}
