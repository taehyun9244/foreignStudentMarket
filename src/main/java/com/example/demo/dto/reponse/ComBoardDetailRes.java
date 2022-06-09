package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import com.example.demo.model.CommunityComment;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ComBoardDetailRes {

    private Long id;
    private String title;
    private String subtitle;
    private String location;
    private String contents;
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    private List<CommunityComment> comment;

    public ComBoardDetailRes(CommunityBoard communityBoard) {
        this.id = communityBoard.getId();
        this.title = communityBoard.getTitle();
        this.subtitle = communityBoard.getSubtitle();
        this.location = communityBoard.getLocation();
        this.contents = communityBoard.getBody();
        this.username = communityBoard.getUser().getUsername();
        this.createdAt = communityBoard.getCreatedAt();
        this.updateAt = communityBoard.getUpdateAt();
        this.comment = communityBoard.getComments();
    }
}
