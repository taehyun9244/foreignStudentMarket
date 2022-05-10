package com.example.demo.model;

import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "CommunityBoard")
@NoArgsConstructor
public class CommunityBoard extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_ID", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String com_title;

    @Column(nullable = false)
    private String com_subtitle;

    @Column(nullable = false)
    private String com_contents;

    @Column(nullable = false)
    private String com_location;

    @Column(nullable = false)
    private String com_country;

    @Column(nullable = false)
    private int countComment;

    @Column(nullable = false)
    private int community_like;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "communityBoard", cascade = CascadeType.REMOVE)
    private List<CommunityComment> comment = new ArrayList<CommunityComment>();

    //커뮤니티 게시글 작성
    public CommunityBoard(ComBoardPostDto postDto, User findUser) {
        this.com_title = postDto.getCom_title();
        this.com_subtitle = postDto.getCom_subtitle();
        this.com_contents = postDto.getCom_contents();
        this.com_location = postDto.getCom_location();
        this.com_country = postDto.getCom_country();
        this.user = findUser;
    }

    //커뮤니티 게시글 수정
    public void editCommunityBoard(ComBoardDetailDto detailDto) {
        this.id = detailDto.getId();
        this.com_title = detailDto.getCom_title();
        this.com_subtitle = detailDto.getCom_subtitle();
        this.com_contents = detailDto.getCom_contents();
        this.com_country = detailDto.getCom_country();
    }

    //댓글 작성시 +1
    public void addComment(int count){
        this.countComment = countComment + 1;
    }

    //댓글 삭제시 -1
    public void removeComment(int count){
        this.countComment = countComment - 1;
    }
}
