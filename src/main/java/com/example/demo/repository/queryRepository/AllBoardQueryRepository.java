package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
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

import static com.example.demo.model.QDeliComment.*;
import static com.example.demo.model.QDeliveryBoard.*;
import static com.example.demo.model.QUser.*;

@Repository
public class AllBoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AllBoardQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //운송 게시글 전체 조회 dto
    public Page<DeliveryBoardSimRes> findByDeliveryBoardDtoAll(Pageable pageable){
        List<DeliveryBoardSimRes> deliBoardListAll = queryFactory
                .select(Projections.constructor(DeliveryBoardSimRes.class,
                        deliveryBoard.id,
                        deliveryBoard.title,
                        deliveryBoard.user.username,
                        deliveryBoard.countComment,
                        deliveryBoard.price,
                        deliveryBoard.from_city,
                        deliveryBoard.from_country,
                        deliveryBoard.createdAt,
                        deliveryBoard.updateAt))
                .from(deliveryBoard)
                .leftJoin(deliveryBoard.user, user)
                .leftJoin(deliveryBoard.deliComment, deliComment)
                .orderBy(deliveryBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliveryBoard.count())
                .from(deliveryBoard)
                .leftJoin(deliveryBoard.user, user)
                .leftJoin(deliveryBoard.deliComment, deliComment);

        return PageableExecutionUtils.getPage(deliBoardListAll, pageable, countQuery :: fetchOne);
    }

    //운송 게시글 전체 조회 entity
    public Page<DeliveryBoard> findByBoardEntityAll(Pageable pageable){
        List<DeliveryBoard> deliBoardListAll = queryFactory
                .selectFrom(deliveryBoard)
                .leftJoin(deliveryBoard.deliComment, deliComment)
                .leftJoin(deliveryBoard.user, user)
                .fetchJoin()
                .orderBy(deliveryBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliveryBoard.count())
                .from(deliveryBoard)
                .leftJoin(deliveryBoard.user, user)
                .leftJoin(deliveryBoard.deliComment, deliComment);

        return PageableExecutionUtils.getPage(deliBoardListAll, pageable, countQuery :: fetchOne);
    }

    //운송 게시글 상세 조회 Dto
    public DeliveryBoardDetailRes findByIdQueryDto(Long deliveryId){
        return queryFactory
                .select(Projections.constructor(DeliveryBoardDetailRes.class,
                        deliveryBoard.id,
                        deliveryBoard.title,
                        deliveryBoard.body.as("contents"),
                        deliveryBoard.from_city,
                        deliveryBoard.from_country,
                        deliveryBoard.to_city.as("to_address"),
                        deliveryBoard.countComment,
                        deliveryBoard.price,
                        deliveryBoard.user.username,
                        deliveryBoard.createdAt,
                        deliveryBoard.updateAt,
                        deliveryBoard.deliComment.as("comment")))
                .from(deliveryBoard)
                .where(boardIdEq(deliveryId))
                .join(deliveryBoard.user, user)
                .join(deliveryBoard.deliComment, deliComment)
                .orderBy(deliComment.createdAt.desc())
                .fetchOne();
    }

    //운송 게시글 상세 조회 entity
    public DeliveryBoard findByBoardIdQueryEntity(Long deliveryId){
        return queryFactory
                .selectFrom(deliveryBoard)
                .where(boardIdEq(deliveryId))
                .leftJoin(deliveryBoard.user, user)
                .leftJoin(deliveryBoard.deliComment, deliComment)
                .orderBy(deliComment.createdAt.desc())
                .fetchJoin()
                .fetchOne();
    }


    private BooleanExpression boardIdEq(Long deliveryId) {
        return deliveryId != null ? deliveryBoard.id.eq(deliveryId) : deliveryBoard.isNull();
    }
}
