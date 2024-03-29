package com.example.demo.repository.queryRepository;


import com.example.demo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpqlBoardQueryRepository {

    private final EntityManager em;

    //운송게시글 전체조회 쿼리성능
    public List<DeliveryBoard> findAllDeliBoard(int offset, int limit) {
        return em.createQuery(
                "select db from DeliveryBoard db" +
                        " join fetch db.user u", DeliveryBoard.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


    //커뮤니티 게시글 전체조회 쿼리성능
    public List<CommunityBoard> findAllComBoard(int offset, int limit) {
        return em.createQuery(
                "select cb from CommunityBoard  cb" +
                        " join fetch cb.user u", CommunityBoard.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    //마켓게시글 전체조회 쿼리성능
    public List<MarketBoard> findAllMarket(int offset, int limit) {
        return em.createQuery(
                "select mk from MarketBoard  mk" +
                        " join fetch mk.user u", MarketBoard.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    //주문 전체조회 쿼리성능
    public List<Order> findAllOrder(int offset, int limit) {
        return em.createQuery(
                "select o from Order  o" +
                        " join fetch o.user u", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    //결제 전체조회 쿼리성능
    public List<Pay> findAllPay(int offset, int limit) {
        return em.createQuery(
                "select p from Pay  p" +
                        " join fetch p.user u", Pay.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
