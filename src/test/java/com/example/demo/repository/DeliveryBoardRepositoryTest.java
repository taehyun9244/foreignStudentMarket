package com.example.demo.repository;

import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.CountryEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.QDeliveryBoard.deliveryBoard;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Transactional
class DeliveryBoardRepositoryTest {

    @Autowired
    private DeliveryBoardRepository deliveryBoardRepository;

    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private UserDetailsImpl userDetailsNull;
    private DeliveryBoard namDeliveryBoard;
    private DeliveryBoard ayaDeliveryBoard;
    private DeliveryBoardPostReq namPostReq;
    private DeliveryBoardPostReq ayaPostReq;

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void setUp(){

        queryFactory = new JPAQueryFactory(em);

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com", "010-1111-1111", namAddress);

        CountryEnum namCountry = CountryEnum.USA;
        namPostReq = new DeliveryBoardPostReq("title", "contents", "NewYork", namCountry, 1000);

        namRegister =  new UserDetailsImpl(nam);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());
        em.persist(namDeliveryBoard);

        //User: aya, ayaBoard: Japan
        CountryEnum ayaCountry = CountryEnum.JP;
        ayaPostReq = new DeliveryBoardPostReq( "title", "contents", "Sibuya", ayaCountry, 99999);

        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com", "080-9999-9999", ayaAddress);

        ayaRegister = new UserDetailsImpl(aya);
        ayaDeliveryBoard = new DeliveryBoard(ayaPostReq, ayaRegister.getUser());
        em.persist(ayaDeliveryBoard);
    }

    @Test
    public void BoardAll(){
        em.flush();
        em.clear();

        List<DeliveryBoard> result = queryFactory
                .selectFrom(deliveryBoard)
                .join(deliveryBoard.user)
                .join(deliveryBoard.deliComment)
                .fetchJoin()
                .orderBy(deliveryBoard.createdAt.desc())
                .fetch();
        for (DeliveryBoard board : result) {
            System.out.println("board = " + board);
        }
    }

//
//    @Test
//    @DisplayName("전체 운송 게시글 조회")
//    void findAllByOrderByCreatedAtDesc() {
//        //given
//        deliveryBoardRepository.save(namDeliveryBoard);
//        deliveryBoardRepository.save(ayaDeliveryBoard);
//
//        //when
//        List<DeliveryBoard> deliveryBoards = deliveryBoardRepository.findAllByOrderByCreatedAtDesc();
//
//        //then
//        assertThat(deliveryBoards.size()).isEqualTo(2);
//        assertThat(deliveryBoards).contains(namDeliveryBoard, ayaDeliveryBoard);
//        assertThat(deliveryBoards).extracting("title").containsExactly("title", "title");
//
//    }

//    @Test
//    @DisplayName("상세 운송 게시글 조회")
//    void findDetailBoard(){
//        //given
//        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);
//        //when
//        DeliveryBoard findBoard = deliveryBoardRepository.f(saveBoard.getId()).orElseGet(
//                ()->deliveryBoardRepository.save(new DeliveryBoard(postReqDto,taehyunRegister.getUser())));
//        //then
//        Assertions.assertThat(findBoard.getId()).isEqualTo(saveBoard.getId());
//    }


//    @Test
//    @DisplayName("운송 게시글 저장")
//    void saveDeliveryBoard(){
//        //given
//        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);
//        //when & then
//        Assertions.assertThat(saveBoard.getId()).isEqualTo(1L);
//    }

//    @Test
//    @DisplayName("운송 게시글 수정")
//    void editDeliveryBoard(){
//        //given
//        DeliveryBoardPostReq postReqDto = new DeliveryBoardPostReq("배송모집합니다 수정", "5명모집이고 상자크기는 '대'입니다",
//                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 70000);
//        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);
//
//        //when
//        DeliveryBoard editBoard = new DeliveryBoard(postReqDto, saveBoard.getUser());
//
//        //then
//        Assertions.assertThat(editBoard.getTitle()).isNotEqualTo(deliveryBoardA.getTitle());
//        Assertions.assertThat(editBoard.getUser()).isEqualTo(deliveryBoardA.getUser());
//    }

//    @Test
//    @DisplayName("운송 게시글 삭제")
//    void deleteDeliveryBoard(){
//        //given
//        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);
//
//        //when
//        Optional<DeliveryBoard> findBoard = deliveryBoardRepository.findById(saveBoard.getId());
//
//        //then
//        findBoard.ifPresent(findBoardId ->{
//            deliveryBoardRepository.delete(findBoardId);
//        });
//        Optional<DeliveryBoard> deleteBoard = deliveryBoardRepository.findById(saveBoard.getId());
//        org.junit.jupiter.api.Assertions.assertFalse(deleteBoard.isPresent());
//    }
}