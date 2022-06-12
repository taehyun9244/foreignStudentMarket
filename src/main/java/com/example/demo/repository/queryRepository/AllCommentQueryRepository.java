package com.example.demo.repository.queryRepository;

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
    public Page<DeliCommentRes> findDeliCommentDto(Long deliveryBoardId, Pageable pageable) {
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

    private BooleanExpression boardIdEq(Long deliveryId) {
        return deliveryId != null ? deliveryBoard.id.eq(deliveryId) : deliveryBoard.isNull();
    }
}
