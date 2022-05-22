package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityComment;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ComCommentRes {
    private Long id;
    private String comComment;
    private String username;
    @Setter
    private Long communityBoardId;



    public ComCommentRes(CommunityComment communityComment) {
        this.id = communityComment.getId();
        this.comComment = communityComment.getComment();
        this.username = communityComment.getUser().getUsername();
        this.communityBoardId = communityComment.getCommunityBoard().getId();
    }
}
