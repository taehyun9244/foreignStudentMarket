package com.example.demo.service;
import com.example.demo.dto.reponse.DeliveryBoardDetailResDto;
import com.example.demo.dto.reponse.DeliveryBoardSimResDto;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.security.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryBoardServiceTest {

    @InjectMocks
    private DeliveryBoardService deliveryBoardService;

    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;

    @Spy
    private PasswordEncoder mockPasswordEncoder;

    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl taehyunRegister;
    private UserDetailsImpl ayakoRegister;
    private DeliveryBoard deliveryBoardA;
    private DeliveryBoard deliveryBoardB;

    @BeforeEach
    public void setUp(){

        //User: null
        userDetailsNull = null;

        //User: Nam, BoardA: Korea
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 50000);
        User taehyun = new User("taehyun", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "Seocho");
        taehyunRegister =  new UserDetailsImpl(taehyun);
        deliveryBoardA = new DeliveryBoard(postReqDto, taehyunRegister.getUser());

        //User: ayako, BoardB: Japan
        DeliveryBoardPostReqDto postReqDto2 = new DeliveryBoardPostReqDto( "미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다",
                "USA", "NewYork", "Japan", "Tokyo-Sibuya", 100000);
        User ayako = new User("ayako", mockPasswordEncoder.encode("1234"),
                "19970528", "ayako@naver.com", "010-2222-2222", "sibuya");
        ayakoRegister = new UserDetailsImpl(ayako);
        deliveryBoardB = new DeliveryBoard(postReqDto2, ayakoRegister.getUser());
    }

    @Test
    @DisplayName("운송 게시판 작성 실패")
    void creatDeliveryBoardFailTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Japan", "Tokyo-Sibuya", 50000);
        UserDetailsImpl userDetails = userDetailsNull;

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails));
    }

    @Test
    @DisplayName("운송 게시판 작성 성공")
    void creatDeliveryBoardTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Japan", "Tokyo-Sibuya", 50000);
        User writer = new User("username", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "Seocho");
        doReturn(new DeliveryBoard(postReqDto, writer)).when(mockDeliveryBoardRepository).save(any(DeliveryBoard.class));

        //when
        DeliveryBoard deliveryBoard = new DeliveryBoard(postReqDto, writer);
        DeliveryBoard saveDeliveryBoard = mockDeliveryBoardRepository.save(deliveryBoard);

        //then
        assertThat(saveDeliveryBoard.getTitle()).isEqualTo(postReqDto.getTitle());
        assertThat(saveDeliveryBoard.getUser().getUsername()).isEqualTo(writer.getUsername());
    }

    @Test
    @DisplayName("운송 게시판 전체 조회")
    void getBoardSimTest() {
        //given
        List<DeliveryBoard> deliveryBoards = new ArrayList<>();
        deliveryBoards.add(deliveryBoardA);
        deliveryBoards.add(deliveryBoardB);

        //when
        when(mockDeliveryBoardRepository.findAllByOrderByCreatedAtDesc()).thenReturn(deliveryBoards);
        List<DeliveryBoardSimResDto> simResDtos = deliveryBoardService.getBoardSim();

        //then
        assertThat(simResDtos.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("운송 게시판 상세 조회 실패")
    void getBoardDetailFailTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto();
        User writer = new User();
        DeliveryBoard saveDeliveryBoard = new DeliveryBoard(postReqDto, writer);

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
        Long boardId = deliveryBoardA.getId();
        when(mockDeliveryBoardRepository.findById(boardId)).thenReturn(Optional.of(deliveryBoardA));

        //when
        DeliveryBoardDetailResDto detailResDto = deliveryBoardService.getBoardDetail(boardId);

        //then
        assertThat(detailResDto.getId()).isEqualTo(deliveryBoardA.getId());
    }

    @Test
    @DisplayName("운송 게시판 작성자 불일치로 수정 실패")
    void editDeliveryBoardFailTest(){
        //given
        UserDetailsImpl writer = ayakoRegister;
        Long boardId = deliveryBoardA.getId();
        when(mockDeliveryBoardRepository.findById(boardId)).thenReturn(Optional.of(deliveryBoardA));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.editDeliveryBoard(boardId, writer, new DeliveryBoardPostReqDto("미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다",
                        "USA", "NewYork", "Japan", "Tokyo-Sibuya", 100000)));
    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 수정 성공")
    void editDeliveryBoardTest() {
        //given
        UserDetailsImpl writer = taehyunRegister;
        Long boardId = deliveryBoardA.getId();
        when(mockDeliveryBoardRepository.findById(boardId)).thenReturn(Optional.of(deliveryBoardA));

        //when
        deliveryBoardService.editDeliveryBoard(boardId, writer, new DeliveryBoardPostReqDto("미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다",
                "USA", "NewYork", "Japan", "Tokyo-Sibuya", 100000));

        //then
        assertThat(deliveryBoardA.getUser()).isEqualTo(writer.getUser());
    }

    @Test
    @DisplayName("운송 게시판 작성자 불일치로 삭제 실패")
    void deleteDeliveryBoardFailTest(){
        //given
        UserDetailsImpl writer = ayakoRegister;
        Long boardId = deliveryBoardA.getId();
        when(mockDeliveryBoardRepository.findById(boardId)).thenReturn(Optional.of(deliveryBoardA));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->deliveryBoardService.deleteDeliveryBoard(boardId, writer));

    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 삭제 성공")
    void deleteDeliveryBoardTest() {
        //given
        UserDetailsImpl writer = taehyunRegister;
        Long boardId = deliveryBoardA.getId();
        when(mockDeliveryBoardRepository.findById(boardId)).thenReturn(Optional.of(deliveryBoardA));

        //when
        deliveryBoardService.deleteDeliveryBoard(boardId, writer);

        //then
        assertThat(deliveryBoardA.getUser()).isEqualTo(writer.getUser());
    }
}