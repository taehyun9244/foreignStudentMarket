package com.example.demo.service;
import com.example.demo.dto.request.DeliveryBoardPostReqDto;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class DeliveryBoardServiceTest {

    @InjectMocks
    private DeliveryBoardService deliveryBoardService;

    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Spy
    private PasswordEncoder mockPasswordEncoder;

    @Test
    @DisplayName("운송 게시판 작성 실패")
    void creatDeliveryBoardFailTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "korea", "seoul-seocho", "japan", "tokyo-sibuya", 50000);
        UserDetailsImpl userDetails = new UserDetailsImpl(new User());

        //when & then
        RuntimeException e = Assertions.assertThrows(RuntimeException.class,()->deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails));
        assertThat(e.getMessage()).isEqualTo("회원가입을 해주세요 가입되지 않았습니다");

    }

    @Test
    @DisplayName("운송 게시판 작성")
    void creatDeliveryBoardTest() {
        //given
        DeliveryBoardPostReqDto postReqDto = new DeliveryBoardPostReqDto("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "korea", "seoul-seocho", "japan", "tokyo-sibuya", 50000);
        UserDetailsImpl userDetails = new UserDetailsImpl(new User("username", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "seocho"));

        //when
        given(mockDeliveryBoardRepository.save(argThat(DeliveryBoard -> DeliveryBoard.getTitle().equals("배송모집합니다"))))
                .willReturn(new DeliveryBoard(postReqDto, userDetails.getUser()));
        deliveryBoardService.creatDeliveryBoard(postReqDto, userDetails);

        //then




    }

    @Test
    @DisplayName("운송 게시판 전체 조회")
    void getBoardSimTest() {
        //given

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
    @DisplayName("운송 게시판 작성자 불일치로 수정 실패")
    void editDliveryBoardFailTest(){
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 수정 성공")
    void editDeliveryBoardTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 작성자 불일치로 삭제 실패")
    void deleteDeliveryBoardFailTest(){
        //given

        //when

        //then
    }

    @Test
    @DisplayName("운송 게시판 작성자 일치로 삭제 성공")
    void deleteDeliveryBoardTest() {
        //given

        //when

        //then
    }
}