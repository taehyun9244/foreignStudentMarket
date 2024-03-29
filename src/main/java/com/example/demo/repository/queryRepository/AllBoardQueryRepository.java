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

import static com.example.demo.model.QCommunityBoard.*;
import static com.example.demo.model.QCommunityComment.*;
import static com.example.demo.model.QDeliComment.*;
import static com.example.demo.model.QDeliveryBoard.*;
import static com.example.demo.model.QMarketBoard.*;
import static com.example.demo.model.QUploadFile.*;
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
                .join(deliveryBoard.user, user)
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
        return queryFactory
                .from(deliveryBoard)
                .leftJoin(deliveryBoard.deliComments, deliComment)
                .join(deliveryBoard.user, user)
                .leftJoin(deliComment.user, user)
                .where(deliveryBoardIdEq(deliveryId))
                .orderBy(deliComment.createdAt.desc())
                .transform(groupBy(deliveryBoard.id)
                        .list(Projections.constructor(
                                DeliveryBoardDetailRes.class,
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
                                list(Projections.constructor(
                                        DeliCommentRes.class,
                                        deliComment.id,
                                        deliComment.comment,
                                        deliComment.user.username,
                                        deliComment.deliveryBoard.id.as("deliveryBoardId")
                                ).as("comments")))));
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
                .join(communityBoard.user, user)
                .on(communityBoard.user.id.eq(user.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(communityBoard.count())
                .from(communityBoard)
                .join(user);

        return PageableExecutionUtils.getPage(communityBoards, pageable, countQuery :: fetchOne);

    }


    //커뮤니티 게시글 상세 조회 Dto
    public List<ComBoardDetailRes> findByCommunityBoardIdDto(Long communityId){
        return queryFactory
                .from(communityBoard)
                .leftJoin(communityBoard.comments, communityComment)
                .leftJoin(communityBoard.user, user)
                .leftJoin(communityComment.user, user)
                .where(communityBoardIdEq(communityId))
                .orderBy(communityComment.createdAt.desc())
                .transform(groupBy(communityBoard.id)
                        .list(Projections.constructor(
                        ComBoardDetailRes.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.subtitle,
                        communityBoard.location,
                        communityBoard.body.as("contents"),
                        communityBoard.user.username,
                        communityBoard.createdAt,
                        communityBoard.updateAt,
                                list(Projections.constructor(
                                        DeliCommentRes.class,
                                        communityComment.id,
                                        communityComment.comment.as("comComment"),
                                        communityComment.user.username,
                                        communityComment.communityBoard.id.as("communityBoardId"))
                                        .as("comments")))));
    }


    //마켓 게시글 전체 조회 Dto
    public Page<MarketSimRes> findMarketBoardAllDto(Pageable pageable){
        List<MarketSimRes> marketBoards = queryFactory
                .select(Projections.constructor(
                        MarketSimRes.class,
                        marketBoard.user.username,
                        marketBoard.location,
                        marketBoard.itemName,
                        marketBoard.price,
                        marketBoard.createdAt))
                .from(marketBoard)
                .join(marketBoard.user, user)
                .on(marketBoard.user.id.eq(user.id))
                .orderBy(marketBoard.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(marketBoard.count())
                .from(marketBoard)
                .join(user);

        return PageableExecutionUtils.getPage(marketBoards, pageable, countQuery :: fetchOne);
    }


    //마켓 게시글 상세 조회 Dto
    public List<MarketDetailRes> findByIdMarketBoardDto(Long marketId){
        return queryFactory
                .from(marketBoard)
                .leftJoin(marketBoard.uploadFiles, uploadFile)
                .join(marketBoard.user, user)
                .where(marketBoardIdEq(marketId))
                .orderBy(uploadFile.createdAt.desc())
                .transform(groupBy(marketBoard.id)
                        .list(Projections.constructor(
                        MarketDetailRes.class,
                        marketBoard.user.username,
                        marketBoard.itemName,
                        marketBoard.body,
                        marketBoard.user.address.city,
                        marketBoard.category,
                        marketBoard.price,
                        marketBoard.createdAt,
                        list(Projections.constructor(
                                ImageFilesRes.class,
                                uploadFile.id,
                                uploadFile.marketBoard.id.as("marketBoardId"),
                                uploadFile.marketBoard.itemName.as("itemName"),
                                uploadFile.createdAt)
                                .as("uploadFiles")))));
    }

    private BooleanExpression deliveryBoardIdEq(Long id) {
        return id != null ? deliveryBoard.id.eq(id) : deliveryBoard.isNull();
    }
    private BooleanExpression communityBoardIdEq(Long id) {
        return id != null ? communityBoard.id.eq(id) : communityBoard.isNull();
    }
    private BooleanExpression marketBoardIdEq(Long id) {
        return id != null ? marketBoard.id.eq(id) : marketBoard.isNull();
    }

//    public DeliveryBoardDetailRes findByBoardIdDto(Long boardId) {
////        queryFactory.select(communityBoard)
//    }
}
