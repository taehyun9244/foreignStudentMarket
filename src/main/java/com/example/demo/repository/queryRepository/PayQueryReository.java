package com.example.demo.repository.queryRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PayQueryReository {
    private final JPAQueryFactory queryFactory;

    public PayQueryReository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
