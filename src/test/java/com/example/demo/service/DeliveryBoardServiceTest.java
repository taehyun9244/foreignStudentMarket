package com.example.demo.service;
import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.queryRepository.JpqlBoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.CountryEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DeliveryBoardServiceTest {

    @InjectMocks
    private DeliveryBoardService deliveryBoardService;

    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;
    @Mock
    private JpqlBoardQueryRepository mockJpqlBoardQueryRepository;
    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private UserDetailsImpl userDetailsNull;
    private DeliveryBoard namDeliveryBoard;
    private DeliveryBoard ayaDeliveryBoard;
    private DeliveryBoardPostReq namPostReq;
    private DeliveryBoardPostReq ayaPostReq;


    @BeforeEach
    public void setUp(){

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        CountryEnum namCountry = CountryEnum.USA;
        namPostReq = new DeliveryBoardPostReq("title", "contents", "NewYork", namCountry, 1000);

        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com", "010-1111-1111", namAddress);

        namRegister =  new UserDetailsImpl(nam);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());

        //User: aya, ayaBoard: Japan
        CountryEnum ayaCountry = CountryEnum.JP;
        ayaPostReq = new DeliveryBoardPostReq( "title", "contents", "Sibuya", ayaCountry, 99999);

        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com", "080-9999-9999", ayaAddress);

        ayaRegister = new UserDetailsImpl(aya);
        ayaDeliveryBoard = new DeliveryBoard(ayaPostReq, ayaRegister.getUser());
    }

    @Test
    @DisplayName("운송 게시판 작성 실패")
    void creatDeliveryBoardFailTest() {
        //given
        UserDetailsImpl userDetails = userDetailsNull;

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.creatDeliveryBoard(namPostReq, userDetails));
    }

    @Test
    @DisplayName("운송 게시판 작성 성공")
    void creatDeliveryBoardTest() {
        //given
        doReturn(new DeliveryBoard(namPostReq, namRegister.getUser())).when(mockDeliveryBoardRepository).save(any(DeliveryBoard.class));

        //when
        DeliveryBoard saveDeliveryBoard = mockDeliveryBoardRepository.save(namDeliveryBoard);

        //then
        assertThat(saveDeliveryBoard.getTitle()).isEqualTo(namPostReq.getTitle());
        assertThat(saveDeliveryBoard.getUser().getUsername()).isEqualTo(namRegister.getUser().getUsername());
    }

    @Test
    @DisplayName("운송 게시판 전체 조회")
    void getBoardSimTest() {
        //given
        List<DeliveryBoard> deliveryBoards = new ArrayList<>();
        deliveryBoards.add(namDeliveryBoard);
        deliveryBoards.add(ayaDeliveryBoard);

        //when
        when(mockJpqlBoardQueryRepository.findAllDeliBoard(0, 1)).thenReturn(deliveryBoards);
        Response<List<DeliveryBoardSimRes>> boardSimResList = deliveryBoardService.getBoardSim(0, 1);
        log.info("boardSimResList = {}", boardSimResList);

        //then
        assertThat(boardSimResList).isEqualTo(2);
    }


    @Test
    @DisplayName("운송 게시판 상세 조회 실패")
    void getBoardDetailFailTest() {
        //given
        DeliveryBoardPostReq nullPostReq = new DeliveryBoardPostReq();
        DeliveryBoard saveDeliveryBoard = new DeliveryBoard(nullPostReq, namRegister.getUser());

        //when & then
        try {
            mockDeliveryBoardRepository.findById(saveDeliveryBoard.getId());
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo("게시글이 없습니다");
            fail();
        }
    }


    @Test
    @DisplayName("운송 게시판 상세 조회 성공")
    void getBoardDetailTest() {
        //given
        when(mockDeliveryBoardRepository.findById(namDeliveryBoard.getId())).thenReturn(Optional.of(namDeliveryBoard));

        //when
        List<DeliveryBoardDetailRes> detailResDto = deliveryBoardService.getBoardDetailV2(namDeliveryBoard.getId());

        //then
        assertThat(detailResDto.get(0)).extracting(String.valueOf(namDeliveryBoard.getId()));
    }

    @Test
    @DisplayName("운송 게시판 작성자 불일치로 수정 실패")
    void editDeliveryBoardFailTest(){
        //given
        Long namBoardId = namDeliveryBoard.getId();
        when(mockDeliveryBoardRepository.findById(namBoardId)).thenReturn(Optional.of(namDeliveryBoard));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.editDeliveryBoard(namBoardId, ayaRegister, namPostReq));
    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 수정 성공")
    void editDeliveryBoardTest() {
        //given
        CountryEnum editCountry = CountryEnum.FR;
        when(mockDeliveryBoardRepository.findById(namDeliveryBoard.getId())).thenReturn(Optional.of(namDeliveryBoard));

        //when
        deliveryBoardService.editDeliveryBoard(namDeliveryBoard.getId(), namRegister,
                new DeliveryBoardPostReq("미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다", "Saitama", editCountry, 100000));

        //then
        assertThat(namDeliveryBoard.getUser()).isEqualTo(namRegister.getUser());
        org.assertj.core.api.Assertions.assertThat(namDeliveryBoard.getTitle()).isEqualTo("미국배송모집합니다");
    }

    @Test
    @DisplayName("운송 게시판 작성자 불일치로 삭제 실패")
    void deleteDeliveryBoardFailTest(){
        //given
        when(mockDeliveryBoardRepository.findById(namDeliveryBoard.getId())).thenReturn(Optional.of(namDeliveryBoard));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.deleteDeliveryBoard(namDeliveryBoard.getId(), ayaRegister));

    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 삭제 성공")
    void deleteDeliveryBoardTest() {
        //given
        when(mockDeliveryBoardRepository.findById(namDeliveryBoard.getId())).thenReturn(Optional.of(namDeliveryBoard));

        //when
        deliveryBoardService.deleteDeliveryBoard(namDeliveryBoard.getId(), namRegister);

        //then
        assertThat(namDeliveryBoard.getUser()).isEqualTo(namRegister.getUser());
    }


}