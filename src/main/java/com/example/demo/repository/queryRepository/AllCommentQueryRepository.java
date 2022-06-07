package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.model.DeliComment;
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

    //운송 게시글 댓글 조회 entity
    public Page<DeliComment> findAllDeliCommentEntity(Long deliveryId, Pageable pageable){
        List<DeliComment> result = queryFactory
                .selectFrom(deliComment)
                .orderBy(deliComment.createdAt.desc())
                .leftJoin(deliComment.user, user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(deliComment.deliveryBoard.id.eq(deliveryId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliComment.count())
                .from(deliComment)
                .leftJoin(deliComment.user, user)
                .leftJoin(deliComment.deliComment, deliComment);

        return PageableExecutionUtils.getPage(result, pageable, countQuery :: fetchOne);

    }

    //운송 게시글 댓글 조회 dto
    public Page<DeliCommentRes> findDeliCommentDto(Long deliveryBoardId, Pageable pageable) {
        List<DeliCommentRes> content = queryFactory
                .select(Projections.constructor(DeliCommentRes.class,
                        deliComment.id,
                        deliComment.comment,
                        user.username,
                        deliveryBoard.id.as("deliveryBoardId")))
                .from(deliComment)
                .orderBy(deliComment.createdAt.desc())
                .join(deliComment.deliveryBoard)
                .leftJoin(deliComment.user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(boardIdEq(deliveryBoardId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliComment.count())
                .from(deliComment)
                .leftJoin(deliComment.user, user)
                .leftJoin(deliComment.deliveryBoard, deliveryBoard);

        return PageableExecutionUtils.getPage(content, pageable, countQuery :: fetchOne);
    }

    private BooleanExpression boardIdEq(Long deliveryId) {
        return deliveryId != null ? deliveryBoard.id.eq(deliveryId) : deliveryBoard.isNull();
    }
}
