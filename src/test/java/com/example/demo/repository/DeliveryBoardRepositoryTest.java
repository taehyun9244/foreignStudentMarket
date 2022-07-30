package com.example.demo.repository;

import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.CountryEnum;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@DataJpaTest
@Transactional
@Slf4j
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


    @BeforeEach
    public void setUp() throws Exception{

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com", "010-1111-1111", namAddress);

        CountryEnum namCountry = CountryEnum.USA;
        namPostReq = new DeliveryBoardPostReq("namTitle", "contents", "NewYork", namCountry, 1000);

        namRegister =  new UserDetailsImpl(nam);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());
        deliveryBoardRepository.save(namDeliveryBoard);

        //User: aya, ayaBoard: Japan
        CountryEnum ayaCountry = CountryEnum.JP;
        ayaPostReq = new DeliveryBoardPostReq( "ayaTitle", "contents", "Sibuya", ayaCountry, 99999);

        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com", "080-9999-9999", ayaAddress);

        ayaRegister = new UserDetailsImpl(aya);
        ayaDeliveryBoard = new DeliveryBoard(ayaPostReq, ayaRegister.getUser());
        deliveryBoardRepository.save(ayaDeliveryBoard);
    }


    @Test
    @DisplayName("전체 운송 게시글 조회")
    void findAllByOrderByCreatedAtDesc() {
        //give && when
        List<DeliveryBoard> findByAllBoard = deliveryBoardRepository.findAllByOrderByCreatedAtDesc();

        //then
        assertThat(findByAllBoard.size()).isEqualTo(2);
        assertThat(findByAllBoard).contains(namDeliveryBoard, ayaDeliveryBoard);

    }

    @Test
    @DisplayName("상세 운송 게시글 조회")
    void findDetailBoard(){
        //given
        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);
        //when
        DeliveryBoard findBoard = deliveryBoardRepository.findById(saveBoard.getId()).orElseGet(
                ()->deliveryBoardRepository.save(new DeliveryBoard(namPostReq,namRegister.getUser())));
        //then
        Assertions.assertThat(findBoard.getId()).isEqualTo(saveBoard.getId());
    }


    //*
    @Test
    @DisplayName("운송 게시글 저장")
    void saveDeliveryBoard(){
        //given
        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);
        //when & then
        Assertions.assertThat(saveBoard.getTitle()).isEqualTo("namTitle");
    }

    @Test
    @DisplayName("운송 게시글 수정")
    void editDeliveryBoard(){
        //given
        DeliveryBoardPostReq postReqDto = new DeliveryBoardPostReq("배송모집합니다 수정", "5명모집이고 상자크기는 '대'입니다",
                "NewYork 수정", CountryEnum.USA, 70000);
        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);

        //when
        DeliveryBoard editBoard = new DeliveryBoard(postReqDto, saveBoard.getUser());

        //then
        Assertions.assertThat(editBoard.getTitle()).isNotEqualTo(namDeliveryBoard.getTitle());
        Assertions.assertThat(editBoard.getUser()).isEqualTo(namDeliveryBoard.getUser());
    }

    @Test
    @DisplayName("운송 게시글 삭제")
    void deleteDeliveryBoard(){
        //given
        DeliveryBoard saveBoard = deliveryBoardRepository.save(namDeliveryBoard);

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