package com.example.demo.service;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliCommentRepository;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.AllCommentQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.CountryEnum.JP;
import static com.example.demo.util.CountryEnum.USA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DeliCommentServiceTest {

    @InjectMocks
    private DeliCommentService deliCommentService;
    @Mock
    private DeliCommentRepository mockDeliCommentRepository;
    @Mock
    private DeliveryBoardRepository mockDeliveryBoardRepository;
    @Mock
    private AllCommentQueryRepository allCommentQueryRepository;
    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder mockPasswordEncoder;
    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private DeliveryBoard namBoard;
    private DeliveryBoard ayaBoard;
    private DeliComment namComment;
    private DeliComment ayaComment;
    private DeliComment deliCommentUserNull;
    private DeliCommentPostReq namCommentReq;
    private DeliCommentPostReq ayaCommentReq;
    private UserDetailsImpl userNullDtail;
    private User userNull;



    @BeforeEach
    public void setUp(){
        //User
        userNull = null;
        userNullDtail = null;

        Address address = new Address("Seoul", "Seocho", "132");
        User taehyun = new User("taehyun", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", address);
        User aya = new User("ayako", mockPasswordEncoder.encode("1234"),
                "19970528", "ayako@naver.com", "010-2222-2222", address);
        namRegister =  new UserDetailsImpl(taehyun);
        ayaRegister =  new UserDetailsImpl(aya);

        //DeliveryBoard postReqDto
        DeliveryBoardPostReq postReqDto1 =
                new DeliveryBoardPostReq("namTitle", "body", "NewYork", USA, 1000);
        DeliveryBoardPostReq postReqDto2 =
                new DeliveryBoardPostReq( "ayaTitle", "body", "Tokyo", JP, 500);
        namBoard = new DeliveryBoard(postReqDto1, taehyun);
        ayaBoard = new DeliveryBoard(postReqDto2, aya);

        //DeliComment postReq
        namCommentReq = new DeliCommentPostReq("댓글", 1L);
        ayaCommentReq = new DeliCommentPostReq("댓글", 1L);
        namComment = new DeliComment(namCommentReq, taehyun, namBoard);
        ayaComment = new DeliComment(ayaCommentReq, aya, ayaBoard);
        deliCommentUserNull = new DeliComment(namCommentReq, userNull, namBoard);

    }

    @Test
    @DisplayName("상세 운송 게시글 댓글 전체 조회 JPA")
    void getDeliComment() {
        //given
        List<DeliComment> deliComments = new ArrayList<>();
        deliComments.add(namComment);
        deliComments.add(ayaComment);
        when(mockDeliCommentRepository.findAllByDeliveryBoardIdOrderByCreatedAtDesc
                (namComment.getDeliveryBoard().getId())).thenReturn(deliComments);

        //when
        List<DeliCommentRes> deliCommentResList = deliCommentService.getDeliComment(namBoard.getId());

        //then
        assertThat(deliCommentResList.size()).isEqualTo(2);
        assertThat(deliCommentResList.contains(namComment));
        assertThat(deliCommentResList.contains(ayaComment));

    }

    @Test
    @DisplayName("상세 운송 게시글 댓글 전체 조회 querydsl")
    void getDeliCommentQuerydsl(){
        //given
        DeliCommentRes deliCommentRes1 = new DeliCommentRes(namComment);
        DeliCommentRes deliCommentRes2 = new DeliCommentRes(ayaComment);

        List<DeliCommentRes> deliComments = new ArrayList<>();
        deliComments.add(deliCommentRes1);
        deliComments.add(deliCommentRes2);

        Page<DeliCommentRes> deliCommentResPage = new PageImpl<>(deliComments);
        given(allCommentQueryRepository.findDeliCommentDto(Pageable.ofSize(10))).willReturn(deliCommentResPage);

        //when
        Page<DeliCommentRes> deliCommentV2 = deliCommentService.getDeliCommentV2(Pageable.ofSize(10));

        //then
        assertThat(deliCommentV2.getTotalElements()).isEqualTo(2);
        assertThat(deliCommentV2.toList().contains(deliCommentRes1));
        assertThat(deliCommentV2.toList().contains(deliCommentRes2));
    }

    @Test
    @DisplayName("비회원 댓글 생성 실패")
    void creatDeliCommentFail(){
        //given & when & then
        assertThrows(RuntimeException.class,
                ()-> deliCommentService.creatDeliComment(userNullDtail, namCommentReq, namBoard.getId()));
    }

    @Test
    @DisplayName("게시글 삭제로 댓글 생성 실패")
    void failCreateCommentNoBoard() {
        //given
        DeliveryBoard nullBoard = new DeliveryBoard();
        DeliCommentPostReq deliCommentPostReq = new DeliCommentPostReq("comment", nullBoard.getId());
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));

        //when && then
        assertThatThrownBy(()-> deliCommentService.creatDeliComment(namRegister, deliCommentPostReq, null))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글입니다");
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void creatDeliComment() {
        //given
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));
        given(mockDeliveryBoardRepository.findById(namBoard.getId())).willReturn(Optional.ofNullable(namBoard));

        //when
        deliCommentService.creatDeliComment(namRegister, namCommentReq, namBoard.getId());

        // then
        verify(mockDeliCommentRepository, times(0)).save(namComment);
    }

    @Test
    @DisplayName("작성자 불일치로 댓글 삭제 실패")
    void deleteDeliCommentFail(){
        //given
        doReturn(new DeliComment(namCommentReq, namRegister.getUser(), namBoard))
                .when(mockDeliCommentRepository).save(any(DeliComment.class));
        DeliComment creatComment = mockDeliCommentRepository.save(namComment);

        //when & then
        assertThrows(RuntimeException.class,
                ()-> deliCommentService.deleteDeliComment(
                        ayaRegister, creatComment.getDeliveryBoard().getId(), creatComment.getId()));

    }

    @Test
    @DisplayName("댓글 존재 하지 않아 삭제 실패")
    void failDeleteNoComment() {
        //when && then
        assertThatThrownBy(()-> deliCommentService.deleteDeliComment(namRegister, null, namBoard.getId()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 댓글입니다");

    }

    @Test
    @DisplayName("게시글 존재 하지 않아 삭제 실패")
    void failDeleteNoComment2() {
        //when && then
        assertThatThrownBy(()-> deliCommentService.deleteDeliComment(namRegister, namComment.getId(), null))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteDeliComment() {
        //given
        given(mockDeliveryBoardRepository.findById(namBoard.getId())).willReturn(Optional.ofNullable(namBoard));
        given(mockDeliCommentRepository.findById(namComment.getId())).willReturn(Optional.ofNullable(namComment));

        //when
        deliCommentService.deleteDeliComment(namRegister, namComment.getId(), namBoard.getId());

        //then
        verify(mockDeliCommentRepository, atLeastOnce()).delete(namComment);
    }

}