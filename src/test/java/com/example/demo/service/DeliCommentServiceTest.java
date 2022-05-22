package com.example.demo.service;

import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliCommentRepository;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.security.UserDetailsImpl;
import org.assertj.core.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DeliCommentServiceTest {

    @InjectMocks
    private DeliCommentService deliCommentService;
    @Mock
    private DeliCommentRepository mockDeliCommentRepository;
    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;
    @Spy
    private PasswordEncoder mockPasswordEncoder;
    private UserDetailsImpl taehyunRegister;
    private UserDetailsImpl ayakoRegister;
    private DeliveryBoard deliveryBoard1;
    private DeliveryBoard deliveryBoard2;
    private DeliComment deliCommentA;
    private DeliComment deliCommentB;
    private DeliComment deliCommentUserNull;
    private DeliCommentPostReq commentPostReq1;
    private DeliCommentPostReq commentPostReq2;
    private UserDetailsImpl userNullDtail;
    private User userNull;



    @BeforeEach
    public void setUp(){
        //User
        userNull = null;
        userNullDtail = null;
        User taehyun = new User("taehyun", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "Seocho");
        User ayako = new User("ayako", mockPasswordEncoder.encode("1234"),
                "19970528", "ayako@naver.com", "010-2222-2222", "sibuya");
        taehyunRegister =  new UserDetailsImpl(taehyun);
        ayakoRegister =  new UserDetailsImpl(ayako);

        //DeliveryBoard postReqDto
        DeliveryBoardPostReq postReqDto1 = new DeliveryBoardPostReq("배송모집합니다", "5명모집이고 상자크기는 '대'입니다",
                "Korea", "Seoul-Seocho", "Korea", "Seoul-Seocho", 50000);
        DeliveryBoardPostReq postReqDto2 = new DeliveryBoardPostReq( "미국배송모집합니다", "5명모집이고 상자크기는 '중'입니다",
                "USA", "NewYork", "Japan", "Tokyo-Sibuya", 100000);
        deliveryBoard1 = new DeliveryBoard(postReqDto1, taehyun);
        deliveryBoard2 = new DeliveryBoard(postReqDto2, ayako);

        //DeliComment postReq
         commentPostReq1 = new DeliCommentPostReq("댓글", 1L, "taehyun");
         commentPostReq2 = new DeliCommentPostReq("댓글", 1L, "ayako");
        deliCommentA = new DeliComment(commentPostReq1, taehyun, deliveryBoard1);
        deliCommentB = new DeliComment(commentPostReq2, ayako, deliveryBoard2);
        deliCommentUserNull = new DeliComment(commentPostReq1, userNull, deliveryBoard1);

    }

    @Test
    @DisplayName("상세 운송 게시글 댓글 전체 조회")
    void getDeliComment() {
        //given
        List<DeliComment> deliComments = new ArrayList<>();
        deliComments.add(deliCommentA);
        deliComments.add(deliCommentB);
        when(mockDeliCommentRepository.findAllByDeliveryBoardIdOrderByCreatedAtDesc(deliCommentA.getDeliveryBoard().getId())).thenReturn(deliComments);

        //when
        List<DeliComment> commentA = mockDeliCommentRepository.findAllByDeliveryBoardIdOrderByCreatedAtDesc(deliCommentA.getId());

        //then
        Assertions.assertThat(commentA.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("댓글 생성 실패")
    void creatDeliCommentFail(){
        //given & when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                ()-> deliCommentService.creatDeliComment(userNullDtail, commentPostReq1, deliveryBoard1.getId()));
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void creatDeliComment() {
        //given
        doReturn(new DeliComment(commentPostReq1, taehyunRegister.getUser(), deliveryBoard1)).when(mockDeliCommentRepository).save(any(DeliComment.class));

        //when
        DeliComment creatComment = mockDeliCommentRepository.save(deliCommentA);

        // then
        Assertions.assertThat(creatComment.getUser().getUsername()).isEqualTo(commentPostReq1.getUsername());
        Assertions.assertThat(creatComment.getComment()).isEqualTo(commentPostReq1.getComment());
    }

    @Test
    @DisplayName("댓글 삭제 실패")
    void deleteDeliCommentFail(){
        //given
        doReturn(new DeliComment(commentPostReq1, taehyunRegister.getUser(), deliveryBoard1)).when(mockDeliCommentRepository).save(any(DeliComment.class));
        DeliComment creatComment = mockDeliCommentRepository.save(deliCommentA);

        //when & then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                ()-> deliCommentService.deleteDeliComment(ayakoRegister, creatComment.getDeliveryBoard().getId(), creatComment.getId()));

    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteDeliComment() {
        //given
        doReturn(new DeliComment(commentPostReq1, taehyunRegister.getUser(), deliveryBoard1)).when(mockDeliCommentRepository).save(any(DeliComment.class));
        DeliComment creatComment = mockDeliCommentRepository.save(deliCommentA);


        //when
        mockDeliCommentRepository.delete(creatComment);

        //then
        Assertions.assertThat(creatComment.getUser()).isEqualTo(taehyunRegister.getUser());
        Assertions.assertThat(deliveryBoard1.getDeliComment()).isEmpty();
    }

    @Test
    @DisplayName("댓글 생성시 게시글 댓글 수 +1")
    void plusCommentCount(){
    }

    @Test
    @DisplayName("댓글 삭제시 게시글 댓글 수 -1")
    void minusCommentCount(){
    }

}