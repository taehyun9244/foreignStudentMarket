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
    private String username;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


    //젠체 게시글 데이터 가공
    public static ComBoardSimResDto ComSimBuild(CommunityBoard board){
        return ComBoardSimResDto.builder()
                .id(board.getId())
                .com_title(board.getCom_title())
                .com_subtitle(board.getCom_subtitle())
                .com_location(board.getCom_location())
                .createdAt(board.getCreatedAt())
                .updateAt(board.getUpdateAt())
                .username(board.getUser().getUsername())
                .build();
    }

    //전체 게시글 가공된 데이터 리스트로 만들기
    public static List<ComBoardSimResDto> list(List<CommunityBoard> communityBoards){
        List<ComBoardSimResDto> comBoardSimResDtoList = new ArrayList<>();
        for(CommunityBoard simResDto : communityBoards){
            comBoardSimResDtoList.add(ComSimBuild(simResDto));
        }
        return comBoardSimResDtoList;
    }



}
