package com.example.demo.dto.reponse;

import com.example.demo.model.CommunityBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ComBoardSimResDto {
    private Long id;
    private String com_title;
    private String com_subtitle;
    private String com_location;
    private String comm_contents;
    private String nickname;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


    public static ComBoardSimResDto ofComSimBuild(CommunityBoard communityBoard){
        return ComBoardSimResDto.builder()
                .id(communityBoard.getId())
                .com_location(communityBoard.getCom_location())
                .com_subtitle(communityBoard.getCom_subtitle())
                .com_title(communityBoard.getCom_title())
                .comm_contents(communityBoard.getComm_contents())
                .createdAt(communityBoard.getCreatedAt())
                .updateAt(communityBoard.getUpdateAt())
                .nickname(communityBoard.getUser().getNickname())
                .build();
    }
    public static List<ComBoardSimResDto> list(List<CommunityBoard> communityBoards){
        List<ComBoardSimResDto> comBoardSimResDtos = new ArrayList<>();
        for(CommunityBoard communityBoard : communityBoards){
            comBoardSimResDtos.add(ofComSimBuild(communityBoard));
        }
        return comBoardSimResDtos;
    }



}
