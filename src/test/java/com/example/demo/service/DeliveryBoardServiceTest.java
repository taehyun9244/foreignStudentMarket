package com.example.demo.service;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.security.UserDetailsImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


class DeliveryBoardServiceTest {

    private final DeliveryBoardRepository deliveryBoardRepository;

    @Autowired
    DeliveryBoardServiceTest(DeliveryBoardRepository deliveryBoardRepository) {
        this.deliveryBoardRepository = deliveryBoardRepository;
    }

    @Test
    @DisplayName("운송 게시판 작성")
    void creatDeliveryBoardTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto(
                "test title", "test contents", "test send_country", "test send_address",
                "test delivered_country", "test delivered_address", 1000);
        User user = new User(
                "userA", "123456789", "20000404", "namtaehyun@naver.com", "010-1234-5678",
                "seocho-gu");
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        DeliveryBoard saveDeliveryBoard =  new DeliveryBoard(postReqDto, userDetails.getUser());

        //when
        DeliveryBoard saveBoard = deliveryBoardRepository.save(saveDeliveryBoard);

        //then
        Optional<DeliveryBoard> findDeliveryBoard = deliveryBoardRepository.findById(saveBoard.getId());
        Assertions.assertThat(findDeliveryBoard).isEqualTo(saveBoard);

    }

//    @Test
//    @DisplayName("운송 게시판 전체 조회")
//    void getBoardSimTest() {
//        //given
//        DeliveryBoardSimResDto simResDto = new DeliveryBoardSimResDto(1L, "test_title"  )
//
//        //when
//
//        //then
//    }
//
//    @Test
//    @DisplayName("운송 게시판 상세 조회")
//    void getBoardDetailTest() {
//        //given
//
//        //when
//
//        //then
//    }
//
//
//    @Test
//    @DisplayName("운송 게시판 수정")
//    void editDeliveryBoardTest() {
//        //given
//
//        //when
//
//        //then
//    }
//
//    @Test
//    @DisplayName("운송 게시판 삭제")
//    void deleteDeliveryBoardTest() {
//        //given
//
//        //when
//
//        //then
//    }
}