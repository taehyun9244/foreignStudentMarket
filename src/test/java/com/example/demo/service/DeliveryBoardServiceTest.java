package com.example.demo.service;

import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class DeliveryBoardServiceTest {

    @Test
    @DisplayName("운송 게시판 전체 조회")
    void getBoardSimTest() {
        //given
        DeliveryBoardSimResDto simResDto = new DeliveryBoardSimResDto(1L, "test_title"  )

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 상세 조회")
    void getBoardDetailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 작성")
    void creatDeliveryBoardTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 수정")
    void editDeliveryBoardTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 삭제")
    void deleteDeliveryBoardTest() {
        //given

        //when

        //then
    }
}