package com.example.demo.dto.reponse;

import com.example.demo.model.DeliComment;
import lombok.Getter;

@Getter
public class DeliCommentResDto {

    private String comment;
    private String username;
    private Long deliveryBoardId;
    private Long userId;

    public DeliCommentResDto(DeliComment deliComments) {
        this.comment = deliComments.getComment();
        this.userId = deliComments.getUser().getId();
        this.username = deliComments.getUser().getUsername();
        this.deliveryBoardId = deliComments.getDeliveryBoard().getId();
    }
}
