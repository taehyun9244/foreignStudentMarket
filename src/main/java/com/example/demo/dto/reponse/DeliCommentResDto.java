package com.example.demo.dto.reponse;

import com.example.demo.model.DeliComment;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public class DeliCommentResDto {
    private Long id;
    private String comment;
    private String username;
    private Long deliveryBoardId;
    public DeliCommentResDto(DeliComment deliComments) {
        this.id = deliComments.getId();
        this.comment = deliComments.getComment();
        this.username = deliComments.getUser().getUsername();
        this.deliveryBoardId = deliComments.getDeliveryBoard().getId();
    }
}
