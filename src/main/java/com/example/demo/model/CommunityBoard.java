package com.example.demo.model;

import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.reponse.ComBoardSimResDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.util.Timesteamed;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "CommunityBoard")
@NoArgsConstructor
public class CommunityBoard extends Timesteamed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_ID", nullable = false)
    private Long id;

    @Column(name = "COMMUNITY_TITLE", nullable = false)
    private String com_title;

    @Column(name = "COMMUNITY_SUBTITLE", nullable = false)
    private String com_subtitle;

    @Column(name = "COMMUNITY_CONTENTS", nullable = false)
    private String com_contents;

    @Column(name = "COMMUNITY_LOCATION", nullable = false)
    private String com_location;

    @Column(name = "COMMUNITY_COUNTRY", nullable = false)
    private String com_country;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public CommunityBoard(Long id, String com_title, String com_subtitle, String com_contents, String com_location, String com_country,
                          LocalDateTime createdAt, LocalDateTime updateAt, User user) {
        this.id = id;
        this.com_title = com_title;
        this.com_subtitle = com_subtitle;
        this.com_contents = com_contents;
        this.com_location = com_location;
        this.com_country = com_country;
        this.user = user;
        setUpdateAt(updateAt);
        setCreatedAt(createdAt);
    }

    //상세 게시글 데이터 가공
    public static CommunityBoard detail(CommunityBoard communityBoard) {
        return CommunityBoard.builder()
                .com_title(communityBoard.getCom_title())
                .com_subtitle(communityBoard.getCom_subtitle())
                .com_location(communityBoard.getCom_location())
                .com_country(communityBoard.getCom_country())
                .com_contents(communityBoard.getCom_contents())
                .createdAt(communityBoard.getCreatedAt())
                .updateAt(communityBoard.getUpdateAt())
                .build();
    }

    //커뮤니티 게시글 작성 데이터 가공
    public CommunityBoard postComBoard(ComBoardPostDto postDto){
        return CommunityBoard.builder()
                .id(postDto.getId())
                .com_title(postDto.getCom_title())
                .com_subtitle(postDto.getCom_subtitle())
                .com_location(postDto.getCom_location())
                .com_country(postDto.getCom_country())
                .com_contents(postDto.getCom_contents())
                .build();
    }

    //커뮤니티 게시글 수정 데이터 가공
    public void editCommunityBoard(ComBoardDetailDto detailDto) {
        this.com_title = getCom_title();
        this.com_subtitle = getCom_subtitle();
        this.com_location = getCom_location();
        this.com_contents = getCom_contents();
        this.com_country = getCom_country();

    }
}
