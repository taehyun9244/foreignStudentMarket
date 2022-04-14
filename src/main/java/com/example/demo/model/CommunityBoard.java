package com.example.demo.model;

import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.util.Timesteamed;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public CommunityBoard(ComBoardPostDto postDto, User findUser) {
        this.id = postDto.getId();
        this.com_title = postDto.getCom_title();
        this.com_subtitle = postDto.getCom_subtitle();
        this.com_contents = postDto.getCom_contents();
        this.com_location = postDto.getCom_location();
        this.com_country = postDto.getCom_country();
        this.user = findUser;
    }

    public void editCommunityBoard(ComBoardDetailDto detailDto) {
        this.id = detailDto.getId();
        this.com_title = detailDto.getCom_title();
        this.com_subtitle = detailDto.getCom_subtitle();
        this.com_contents = detailDto.getCom_contents();
        this.com_country = detailDto.getCom_country();
    }
}
