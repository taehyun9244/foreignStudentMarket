package com.example.demo.model;

import com.example.demo.dto.request.CommentPostReq;
import com.example.demo.util.Timestamped;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;
    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private CommunityBoard communityBoard;

    @ManyToOne
    @JoinColumn(name = "DeliveryId")
    private DeliveryBoard deliveryBoard;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Comment(CommentPostReq postReq, User findUser, DeliveryBoard findBoardId) {
        this.comment = postReq.getComment();
        this.user = findUser;
        this.deliveryBoard = findBoardId;
    }

}
