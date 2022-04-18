package com.example.demo.model;

import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class DeliComment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DeliveryId")
    private DeliveryBoard deliveryBoard;

    public DeliComment(DeliCommentPostReq postReq, User writer, DeliveryBoard existBoard) {
        this.comment = postReq.getComment();
        this.user = writer;
        this.deliveryBoard = existBoard;
    }

}
