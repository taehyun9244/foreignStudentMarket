package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.ComCommentRes;
import com.example.demo.dto.reponse.DeliCommentRes;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.demo.model.QCommunityBoard.*;
import static com.example.demo.model.QCommunityComment.*;
import static com.example.demo.model.QDeliComment.deliComment;
import static com.example.demo.model.QDeliveryBoard.deliveryBoard;
import static com.example.demo.model.QUser.user;

@Repository
public class AllCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AllCommentQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //운송 게시글 댓글 조회 dto
    public Page<DeliCommentRes> findDeliCommentDto(Long deliveryBoardId, Pageable pageable){
        List<DeliCommentRes> content = queryFactory
                .select(Projections.constructor(DeliCommentRes.class,
                        deliComment.id,
                        deliComment.comment,
                        deliComment.user.username,
                        deliComment.deliveryBoard.id.as("deliveryBoardId")))
                .from(deliComment)
                .join(user)
                .on(deliComment.user.id.eq(user.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(boardIdEq(deliveryBoardId))
                .orderBy(deliComment.createdAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliComment.count())
                .from(deliComment)
                .join(deliComment.user, user)
                .join(deliComment.deliveryBoard, deliveryBoard);

        return PageableExecutionUtils.getPage(content, pageable, countQuery :: fetchOne);
    }

    //커뮤니티 게시글 댓글 조회 dto
    public Page<ComCommentRes> findComCommentDto(Long communityBoardId, Pageable pageable){
        List<ComCommentRes> comComments = queryFactory
                .select(Projections.constructor(ComCommentRes.class,
                        communityComment.id,
                        communityComment.comment.as("comComment"),
                        communityComment.user.username,
                        communityComment.communityBoard.id.as("communityBoardId")))
                .from(communityComment)
                .join(user)
                .on(communityComment.user.id.eq(user.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(boardIdEq(communityBoardId))
                .orderBy(communityComment.createdAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(communityComment.count())
                .from(communityComment)
                .join(communityComment.user, user)
                .join(communityComment.communityBoard, communityBoard);

        return PageableExecutionUtils.getPage(comComments, pageable, countQuery :: fetchOne);

    }

    private BooleanExpression boardIdEq(Long deliveryId) {
        return deliveryId != null ? deliveryBoard.id.eq(deliveryId) : deliveryBoard.isNull();
    }
}
