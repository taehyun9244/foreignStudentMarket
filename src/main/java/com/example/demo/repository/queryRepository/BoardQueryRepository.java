package com.example.demo.repository.queryRepository;


import com.example.demo.model.CommunityBoard;
import com.example.demo.model.DeliveryBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final EntityManager em;


    public List<DeliveryBoard> findAllDeliBoard(int offset, int limit) {
        return em.createQuery(
                "select db from DeliveryBoard db" +
                        " join fetch db.user u", DeliveryBoard.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<CommunityBoard> findAllComBoard(int offset, int limit) {
        return em.createQuery(
                "select cb from CommunityBoard  cb" +
                        " join fetch cb.user u", CommunityBoard.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
