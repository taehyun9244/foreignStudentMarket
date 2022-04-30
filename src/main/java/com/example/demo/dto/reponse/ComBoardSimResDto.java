package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class ComBoardSimResDto {
    private Long id;
    private String com_title;
    private String com_subtitle;
    private String com_location;
    private String username;
    private int commentCount;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


    public ComBoardSimResDto(CommunityBoard communityBoard) {
        this.id = communityBoard.getId();
        this.com_title = communityBoard.getCom_title();
        this.com_subtitle = communityBoard.getCom_subtitle();
        this.com_location = communityBoard.getCom_location();
        this.username = communityBoard.getUser().getUsername();
        this.commentCount = communityBoard.getCountComment();
        this.createdAt = communityBoard.getCreatedAt();
        this.updateAt = communityBoard.getUpdateAt();
    }
}
