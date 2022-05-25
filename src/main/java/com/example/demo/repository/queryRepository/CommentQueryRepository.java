package com.example.demo.repository.queryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepository {

    private final EntityManager em;

    //운송게시글 id의 댓글 전체 조회 쿼리성능
//    public List<DeliComment> findAllDeliComment(Long deliveryBoardId){
//        return em.createQuery(
//                "select distinct dc from DeliComment dc where dc.deliveryBoard =: deliveryBoardId", DeliComment.class)
//                .setParameter("deliveryBoardId", deliveryBoardId)
//                .getResultList();
////                        " join fetch dc.user u" +
////                        " join fetch dc.deliveryBoard d", DeliComment.class)
//    }

        //-> ManyToOne은 fetch join으로 해결해주고, OneToMany는 Batch 성능 해결
}
