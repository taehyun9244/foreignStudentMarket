package com.example.demo.repository;

import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class DeliveryBoardRepositoryTest {

    @Autowired
    private DeliveryBoardRepository deliveryBoardRepository;

    private UserDetailsImpl taehyunRegister;
    private UserDetailsImpl ayakoRegister;
    private DeliveryBoard deliveryBoardA;
    private DeliveryBoard deliveryBoardB;

    @BeforeEach
    public void setUp(){

        //User: Nam, BoardA: Korea
        DeliveryBoardPostReq postReqDto = new DeliveryBoardPostReq("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 50000);
        User taehyun = new User("taehyun","1234",
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "Seocho");
        taehyunRegister =  new UserDetailsImpl(taehyun);
        deliveryBoardA = new DeliveryBoard(postReqDto, taehyunRegister.getUser());

        //User: ayako, BoardB: Japan
        DeliveryBoardPostReq postReqDto2 = new DeliveryBoardPostReq("미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다",
                "USA", "NewYork", "Japan", "Tokyo-Sibuya", 100000);
        User ayako = new User("ayako","1234",
                "19970528", "ayako@naver.com", "010-2222-2222", "sibuya");
        ayakoRegister = new UserDetailsImpl(ayako);
        deliveryBoardB = new DeliveryBoard(postReqDto2, ayakoRegister.getUser());
    }

    @Test
    @DisplayName("전체 운송 게시글 조회")
    void findAllByOrderByCreatedAtDesc() {
        //given
        deliveryBoardRepository.save(deliveryBoardA);
        deliveryBoardRepository.save(deliveryBoardB);

        //when
        List<DeliveryBoard> deliveryBoards = deliveryBoardRepository.findAllByOrderByCreatedAtDesc();

        //then
        Assertions.assertThat(deliveryBoards.size()).isEqualTo(2);
        Assertions.assertThat(deliveryBoards).contains(deliveryBoardA, deliveryBoardB);
    }

    @Test
    @DisplayName("상세 운송 게시글 조회")
    void findDetailBoard(){
        //given
        DeliveryBoardPostReq postReqDto = new DeliveryBoardPostReq("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 50000);
        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);
        //when
        DeliveryBoard findBoard = deliveryBoardRepository.findById(saveBoard.getId()).orElseGet(
                ()->deliveryBoardRepository.save(new DeliveryBoard(postReqDto,taehyunRegister.getUser())));
        //then
        Assertions.assertThat(findBoard.getId()).isEqualTo(saveBoard.getId());
    }


    @Test
    @DisplayName("운송 게시글 저장")
    void saveDeliveryBoard(){
        //given
        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);
        //when & then
        Assertions.assertThat(saveBoard.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("운송 게시글 수정")
    void editDeliveryBoard(){
        //given
        DeliveryBoardPostReq postReqDto = new DeliveryBoardPostReq("배송모집합니다 수정", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 70000);
        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);

        //when
        DeliveryBoard editBoard = new DeliveryBoard(postReqDto, saveBoard.getUser());

        //then
        Assertions.assertThat(editBoard.getTitle()).isNotEqualTo(deliveryBoardA.getTitle());
        Assertions.assertThat(editBoard.getUser()).isEqualTo(deliveryBoardA.getUser());
    }

    @Test
    @DisplayName("운송 게시글 삭제")
    void deleteDeliveryBoard(){
        //given
        DeliveryBoard saveBoard = deliveryBoardRepository.save(deliveryBoardA);

        //when
        Optional<DeliveryBoard> findBoard = deliveryBoardRepository.findById(saveBoard.getId());

        //then
        findBoard.ifPresent(findBoardId ->{
            deliveryBoardRepository.delete(findBoardId);
        });
        Optional<DeliveryBoard> deleteBoard = deliveryBoardRepository.findById(saveBoard.getId());
        org.junit.jupiter.api.Assertions.assertFalse(deleteBoard.isPresent());
    }
}