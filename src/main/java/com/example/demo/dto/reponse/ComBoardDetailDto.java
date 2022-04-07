package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
public class ComBoardDetailDto {

    private Long id;
    private String title;
    private String subtitle;
    private String location;
    private String country;
    private String contents;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    public static ComBoardDetailDto detail(CommunityBoard communityBoard) {
        return ComBoardDetailDto.builder()
                .id(communityBoard.getId())
                .title(communityBoard.getCom_title())
                .subtitle(communityBoard.getCom_subtitle())
                .location(communityBoard.getCom_location())
                .country(communityBoard.getCom_country())
                .contents(communityBoard.getComm_contents())
                .createdAt(communityBoard.getCreatedAt())
                .updateAt(communityBoard.getUpdateAt())
                .build();
    }
}
