package com.example.demo.model;

import com.example.demo.dto.request.ComCommentPostReq;
import com.example.demo.util.Timestamped;
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

    @Column(name = "COMMUNITY_COMMENT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CommunityBoardId")
    private CommunityBoard communityBoard;

    public CommunityComment(User findUser, ComCommentPostReq postReq, CommunityBoard findCommunityBoard) {
        this.comment = postReq.getComComment();
        this.user = findUser;
        this.communityBoard =findCommunityBoard;
    }
}
