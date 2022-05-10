package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import com.example.demo.model.CommunityComment;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ComBoardDetailDto {

    private Long id;
    private String com_title;
    private String com_subtitle;
    private String com_location;
    private String com_country;
    private String com_contents;
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    private List<CommunityComment> comment;

    public ComBoardDetailDto(CommunityBoard communityBoard) {
        this.id = communityBoard.getId();
        this.com_title = communityBoard.getCom_title();
        this.com_subtitle = communityBoard.getCom_subtitle();
        this.com_location = communityBoard.getCom_location();
        this.com_country = communityBoard.getCom_country();
        this.com_contents = communityBoard.getCom_contents();
        this.username = communityBoard.getUser().getUsername();
        this.createdAt = communityBoard.getCreatedAt();
        this.updateAt = communityBoard.getUpdateAt();
        this.comment = communityBoard.getComment();
    }
}
