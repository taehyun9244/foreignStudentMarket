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
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String location;

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
    public CommunityBoard(ComBoardPostDto postDto, User writer) {
        this.title = postDto.getTitle();
        this.subtitle = postDto.getSubtitle();
        this.body = postDto.getContents();
        this.location = postDto.getLocation();
        this.user = writer;
    }

    //커뮤니티 게시글 수정
    public void editCommunityBoard(ComBoardDetailDto editDto) {
        this.id = editDto.getId();
        this.title = editDto.getTitle();
        this.subtitle = editDto.getSubtitle();
        this.body = editDto.getContents();
        this.location = editDto.getLocation();
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
