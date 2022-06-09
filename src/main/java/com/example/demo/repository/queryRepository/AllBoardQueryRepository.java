package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.*;
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
import java.util.Map;

import static com.example.demo.model.QCommunityBoard.*;
import static com.example.demo.model.QCommunityComment.*;
import static com.example.demo.model.QDeliComment.*;
import static com.example.demo.model.QDeliveryBoard.*;
import static com.example.demo.model.QUser.*;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
public class AllBoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AllBoardQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //운송 게시글 전체 조회 dto
    public Page<DeliveryBoardSimRes> findByDeliveryBoardAllDto(Pageable pageable){
        List<DeliveryBoardSimRes> deliveryBoards = queryFactory
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
                .join(user)
                .on(deliveryBoard.user.id.eq(user.id))
                .orderBy(deliveryBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(deliveryBoard.count())
                .from(deliveryBoard)
                .join(user);

        return PageableExecutionUtils.getPage(deliveryBoards, pageable, countQuery :: fetchOne);
    }


    //운송 게시글 상세 조회 Dto
    public List<DeliveryBoardDetailRes> findByDeliveryBoardIdDto(Long deliveryId){
        return  queryFactory
                .from(deliveryBoard)
                .join(deliveryBoard.user, user)
                .on(deliveryBoard.user.id.eq(user.id))
                .leftJoin(deliveryBoard.deliComments, deliComment)
                .on(deliveryBoard.id.eq(deliComment.deliveryBoard.id))
                .where(boardIdEq(deliveryId))
                .orderBy(deliComment.createdAt.desc())
                .transform(groupBy(deliveryBoard.id).list(Projections.constructor(DeliveryBoardDetailRes.class,
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
                                list(Projections.constructor(DeliCommentRes.class,
                                        deliComment.id,
                                        deliComment.comment,
                                        deliComment.user.username,
                                        deliComment.deliveryBoard.id.as("deliveryBoardId")).as("comments")))));

    }

    //커뮤니티 게시글 전체 조회 Dto
    public Page<ComBoardSimRes> findCommunityBoardAllDto(Pageable pageable){
        List<ComBoardSimRes> communityBoards = queryFactory
                .select(Projections.constructor(ComBoardSimRes.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.subtitle,
                        communityBoard.location,
                        communityBoard.user.username,
                        communityBoard.countComment.as("commentCount"),
                        communityBoard.createdAt,
                        communityBoard.updateAt))
                .from(communityBoard)
                .orderBy(communityBoard.createdAt.desc())
                .leftJoin(communityBoard.user, user)
                .leftJoin(communityBoard.comment, communityComment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(communityBoard.count())
                .from(communityBoard)
                .leftJoin(communityBoard.user, user)
                .leftJoin(communityBoard.comment, communityComment);

        return PageableExecutionUtils.getPage(communityBoards, pageable, countQuery :: fetchOne);

    }

    //커뮤니티 게시글 상세 조회 Dto
    public ComBoardDetailRes findByCommunityBoardIdDto(Long communityId){
        return queryFactory
                .select(Projections.constructor(ComBoardDetailRes.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.subtitle,
                        communityBoard.location,
                        communityBoard.body.as("contents"),
                        communityBoard.user.username,
                        communityBoard.createdAt,
                        communityBoard.updateAt,
                        Projections.list(
                                Projections.constructor(ComCommentRes.class,
                                        communityComment.id,
                                        communityComment.comment.as("comComment"),
                                        communityComment.user.username,
                                        communityComment.communityBoard.id.as("communityBoardId")))
                ))
                .from(communityBoard)
                .join(communityBoard.user, user)
                .join(communityBoard.comment, communityComment)
                .where(boardIdEq(communityId))
                .fetchOne();
    }

    private BooleanExpression boardIdEq(Long id) {
        return id != null ? deliveryBoard.id.eq(id) : deliveryBoard.isNull();
    }
}
