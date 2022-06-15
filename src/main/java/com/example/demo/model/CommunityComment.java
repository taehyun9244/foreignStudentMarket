package com.example.demo.model;

import com.example.demo.dto.request.ComCommentPostReq;
import com.example.demo.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CommunityComment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_COMMENT_ID")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CommunityBoardId")
    @JsonIgnore
    private CommunityBoard communityBoard;

    // 커뮤니티댓글 작성 생성자
    public CommunityComment(User findUser, ComCommentPostReq postReq, CommunityBoard findCommunityBoard) {
        this.comment = postReq.getComment();
        this.user = findUser;
        this.communityBoard =findCommunityBoard;
    }
}
