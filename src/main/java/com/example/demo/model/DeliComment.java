package com.example.demo.model;

import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class DeliComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "deliveryId")
    @JsonIgnore
    private DeliveryBoard deliveryBoard;

    //운송 댓글작성 생성자
    public DeliComment(DeliCommentPostReq postReq, User writer, DeliveryBoard existBoard) {
        this.comment = postReq.getComment();
        this.user = writer;
        this.deliveryBoard = existBoard;
    }

}
