package com.example.demo.repository.queryRepository;

import com.example.demo.model.DeliComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepository {

    private final EntityManager em;


    public List<DeliComment> findAllDeliComment(Long deliveryBoardId){
        return em.createQuery(
                "select distinct dc from DeliComment dc" +
                        " join fetch dc.user u" +
                        " join fetch dc.deliveryBoard d", DeliComment.class)
                .getResultList();
    }
}