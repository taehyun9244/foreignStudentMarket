package com.example.demo.service;

import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DeliveryBoardServiceTest {

    @InjectMocks
    private DeliveryBoardService deliveryBoardService;

    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;
    @Mock
    private JpqlBoardQueryRepository mockJpqlBoardQueryRepository;
    @Mock
    private AllBoardQueryRepository allBoardQueryRepository;
    @Mock
    private AllBoardQueryRepository queryRepository;
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
        namPostReq = new DeliveryBoardPostReq("namTitle", "contents", "NewYork", namCountry, 1000);

        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com", "010-1111-1111", namAddress);

        namRegister =  new UserDetailsImpl(nam);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());

        //User: aya, ayaBoard: Japan
        CountryEnum ayaCountry = CountryEnum.JP;
        ayaPostReq = new DeliveryBoardPostReq( "ayaTitle", "contents", "Sibuya", ayaCountry, 99999);

        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com", "080-9999-9999", ayaAddress);

        ayaRegister = new UserDetailsImpl(aya);
        ayaDeliveryBoard = new DeliveryBoard(ayaPostReq, ayaRegister.getUser());
    }

    @Test
    @DisplayName("비회원 운송 게시판 작성 실패")
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
    @DisplayName("운송 게시판 전체 조회 jpql")
    void getBoardSimTest() {
        //given
        List<DeliveryBoard> deliveryBoards = new ArrayList<>();
        deliveryBoards.add(namDeliveryBoard);
        deliveryBoards.add(ayaDeliveryBoard);

        //when
        when(mockJpqlBoardQueryRepository.findAllDeliBoard(0, 1)).thenReturn(deliveryBoards);
        List<DeliveryBoardSimRes> boardSimResList = deliveryBoardService.getBoardSim(0, 1);
        log.info("boardSimResList = {}", boardSimResList);

        //then
        assertThat(deliveryBoards).extracting("title").containsExactly("namTitle", "ayaTitle");
    }

    @Test
    @DisplayName("운송 게시판 전체 조회 querydsl")
    void findAllBoardQuerydsl() {
        //given
        List<DeliveryBoardSimRes> boardSimResList = new ArrayList<>();
        DeliveryBoardSimRes deliveryBoardSimRes1 = new DeliveryBoardSimRes(namDeliveryBoard);
        DeliveryBoardSimRes deliveryBoardSimRes2 = new DeliveryBoardSimRes(ayaDeliveryBoard);

        boardSimResList.add(deliveryBoardSimRes1);
        boardSimResList.add(deliveryBoardSimRes2);

        Page<DeliveryBoardSimRes> deliveryBoardSimResPage = new PageImpl<>(boardSimResList);

        given(allBoardQueryRepository.findByDeliveryBoardAllDto(Pageable.ofSize(10))).willReturn(deliveryBoardSimResPage);

        //when
        Page<DeliveryBoardSimRes> findAllBoard = deliveryBoardService.getBoardSimV2(Pageable.ofSize(10));

        //then
        assertThat(findAllBoard.getTotalElements()).isEqualTo(2);
        assertThat(findAllBoard.toList().contains(namDeliveryBoard));
        assertThat(findAllBoard.toList().contains(ayaDeliveryBoard));

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
        List<DeliveryBoard> deliveryBoards = new ArrayList<>();
        deliveryBoards.add(namDeliveryBoard);
        deliveryBoards.add(ayaDeliveryBoard);
        List<DeliveryBoardDetailRes> findBoardById = queryRepository.findByDeliveryBoardIdDto(1L);

        //when
        List<DeliveryBoardDetailRes> findBoardId = deliveryBoardService.getBoardDetailV2(1L);

        //then
        assertThat(findBoardId).isEqualTo(findBoardById);
    }

    @Test
    @DisplayName("운송 게시글 상세 조회 jpa")
    void findOneBoardJpa() {
        //given
        given(mockDeliveryBoardRepository.findById(namDeliveryBoard.getId())).willReturn(Optional.ofNullable(namDeliveryBoard));

        //when
        DeliveryBoardDetailRes findOne = deliveryBoardService.getBoardDetailV1(namDeliveryBoard.getId());

        //then
        assertThat(findOne.getId()).isEqualTo(namDeliveryBoard.getId());
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
        assertThat(namDeliveryBoard.getTitle()).isEqualTo("미국배송모집합니다");
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

    @Test
    @DisplayName("게시글 존재 하지 않아 수정 불가")
    void failEditNoBoard() {
        //when && then
        assertThatThrownBy(()-> deliveryBoardService.editDeliveryBoard(null, namRegister, ayaPostReq))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글입니다");

    }

    @Test
    @DisplayName("게시글 존재 하지 않아 삭제 불가")
    void failDeleteNoBoard() {
        //when && then
        assertThatThrownBy(()-> deliveryBoardService.deleteDeliveryBoard(null, namRegister))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글입니다");
    }

    @Test
    @DisplayName("게시글 존재하지 않아 상세조회 불가Jpa")
    void failFindOneBoardJpa() {
        //when && then
        assertThatThrownBy(()-> deliveryBoardService.getBoardDetailV1(null))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글입니다");
    }

}