package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliCommentPostReq {
    private String comment;
    private Long deliveryBoardId;
}
