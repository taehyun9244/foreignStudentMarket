package com.example.demo.dto.request;

import lombok.Getter;

@Getter
public class DeliCommentPostReq {
    private String comment;
    private Long deliveryBoardId;
    private Long userId;
}
