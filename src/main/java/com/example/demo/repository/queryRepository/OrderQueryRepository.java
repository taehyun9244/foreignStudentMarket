package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.OrderListRes;
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

import static com.example.demo.model.QMarketBoard.*;
import static com.example.demo.model.QOrder.*;
import static com.example.demo.model.QUser.*;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    public Page<OrderListRes> findOrderList(Pageable pageable, String username){
        List<OrderListRes> orders = queryFactory
                .select(Projections.constructor(
                        OrderListRes.class,
                        order.id.as("orderId"),
                        order.marketBoard.id.as("marketId"),
                        order.marketBoard.itemName,
                        order.marketBoard.price,
                        order.orderStatus,
                        order.marketBoard.user.username.as("seller"),
                        order.user.username.as("buyer"),
                        order.user.address.as("to_address")
                ))
                .from(order)
                .join(order.user)
                .on(order.user.id.eq(user.id))
                .join(order.marketBoard)
                .on(order.marketBoard.id.eq(marketBoard.id))
                .orderBy(order.createdAt.desc())
                .where(existUserEq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.count())
                .from(order)
                .join(user);

        return PageableExecutionUtils.getPage(orders, pageable, countQuery :: fetchOne);
    }

    private BooleanExpression existUserEq(String username) {
        return username != null ? order.user.username.eq(username) : order.isNull();
    }
}
