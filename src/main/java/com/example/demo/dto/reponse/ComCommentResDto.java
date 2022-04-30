package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityComment;
import lombok.Getter;

@Getter
public class ComCommentResDto {
    private Long id;
    private String comComment;
    private String username;
    private Long communityBoardId;

    public ComCommentResDto(CommunityComment communityComment) {
        this.id = communityComment.getId();
        this.comComment = communityComment.getComment();
        this.username = communityComment.getUser().getUsername();
        this.communityBoardId = communityComment.getCommunityBoard().getId();
    }
}
