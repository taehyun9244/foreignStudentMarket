package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class ComBoardSimResDto {
    private Long id;
    private String title;
    private String subtitle;
    private String location;
    private String username;
    private int commentCount;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


    public ComBoardSimResDto(CommunityBoard communityBoard) {
        this.id = communityBoard.getId();
        this.title = communityBoard.getTitle();
        this.subtitle = communityBoard.getSubtitle();
        this.location = communityBoard.getLocation();
        this.username = communityBoard.getUser().getUsername();
        this.commentCount = communityBoard.getCountComment();
        this.createdAt = communityBoard.getCreatedAt();
        this.updateAt = communityBoard.getUpdateAt();
    }
}
