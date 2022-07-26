package com.example.demo.repository.queryRepository;

import com.example.demo.dto.reponse.PayListRes;
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

import static com.example.demo.model.QOrder.order;
import static com.example.demo.model.QPay.*;
import static com.example.demo.model.QUser.*;


@Repository
public class PayQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PayQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<PayListRes> findPayList(String username, Pageable pageable){
        List<PayListRes> pays = queryFactory
                .select(Projections.constructor(
                        PayListRes.class,
                        pay.order.id.as("orderId"),
                        pay.itemName,
                        pay.itemPrice,
                        pay.payStatus,
                        pay.user.address.as("to_address")
                ))
                .from(pay)
                .join(pay.user, user)
                .on(pay.user.id.eq(user.id))
                .orderBy(pay.createdAt.desc())
                .where(existUserEq(username))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(pay.count())
                .from(pay)
                .join(user);

        return PageableExecutionUtils.getPage(pays, pageable, countQuery :: fetchOne);
    }

    private BooleanExpression existUserEq(String username) {
        return username != null ? order.user.username.eq(username) : order.isNull();
    }

}
