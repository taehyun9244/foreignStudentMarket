package com.example.demo.service;

import com.example.demo.dto.reponse.ComCommentRes;
import com.example.demo.dto.request.ComBoardPostReq;
import com.example.demo.dto.request.ComCommentPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.CommunityComment;
import com.example.demo.model.User;
import com.example.demo.repository.ComCommentRepository;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.queryRepository.AllCommentQueryRepository;
import com.example.demo.security.UserDetailsImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComCommentServiceTest {

    @InjectMocks
    private ComCommentService commentService;
    @Mock
    private ComCommentRepository comCommentRepository;
    @Mock
    private CommunityRepository boardRepository;
    @Mock
    private AllCommentQueryRepository allCommentQueryRepository;
    private UserDetailsImpl namRegister;
    private UserDetailsImpl userDetailsNull;
    private CommunityBoard namCommunityBoard;
    private ComBoardPostReq namPostReq;
    private CommunityComment namComment;
    private ComCommentPostReq commentPostReq;

    @BeforeEach
    public void setUp(){

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: Seocho3dong
        namPostReq = new ComBoardPostReq("namTitle", "namSubTitle", "namContents", "Seocho3dong");
        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com", "010-1111-1111", namAddress);
        namRegister =  new UserDetailsImpl(nam);
        namCommunityBoard = new CommunityBoard(namPostReq, namRegister.getUser());

        //comment
        commentPostReq = new ComCommentPostReq("comment", namCommunityBoard.getId());
        namComment = new CommunityComment(nam, commentPostReq, namCommunityBoard);

    }


    @Test
    @DisplayName("전체 댓글 조회 querydsl")
    void findAllComment() {
        //given
        ComCommentRes commentRes = new ComCommentRes(namComment);
        List<ComCommentRes> comCommentResList = new ArrayList<>();
        comCommentResList.add(commentRes);

        Page<ComCommentRes> commentResPage = new PageImpl<>(comCommentResList);

        when(allCommentQueryRepository.findComCommentDto(Pageable.ofSize(10))).thenReturn(commentResPage);

        //when
        Page<ComCommentRes> findAll = commentService.getComCommentV2(Pageable.ofSize(10));

        //then
        assertThat(findAll.getSize()).isEqualTo(1);
        assertThat(findAll.getContent()).isEqualTo(comCommentResList);
    }

    @Test
    @DisplayName("전체 댓글 조회 jpa")
    void findAllCommentJpa() {
        //given
        List<CommunityComment> communityComments = new ArrayList<>();
        communityComments.add(namComment);
        communityComments.add(namComment);

        given(comCommentRepository.findAllByCommunityBoardIdOrderByCreatedAtDesc(namCommunityBoard.getId()))
                .willReturn(communityComments);

        //when
        List<ComCommentRes> comComment = commentService.getComComment(namCommunityBoard.getId());

        //then
        assertThat(comComment.size()).isEqualTo(2);
        assertThat(comComment.contains(namComment));
    }

    @Test
    @DisplayName("댓글 생성")
    void creatComment() {
        //given && when
        doReturn(new CommunityComment(namRegister.getUser(), commentPostReq, namCommunityBoard))
                .when(comCommentRepository).save(any(CommunityComment.class));

        CommunityComment communityComment = comCommentRepository.save(namComment);

        //then
        assertThat(communityComment.getCommunityBoard()).isEqualTo(namCommunityBoard);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        //given
        when(boardRepository.findById(namCommunityBoard.getId())).thenReturn(Optional.ofNullable(namCommunityBoard));
        when(comCommentRepository.findById(namComment.getId())).thenReturn(Optional.ofNullable(namComment));

        //when
        commentService.deleteComComment(namRegister, namComment.getId(), namCommunityBoard.getId());

        //then
        verify(comCommentRepository, atLeastOnce()).delete(namComment);

    }

    @Test
    @DisplayName("게시글이 존재하지 않아 댓글 생성 실패")
    void failCommentNoBoard() {
        //given
        CommunityBoard nullBoard = new CommunityBoard();

        //when && then
        Assertions.assertThrows(RuntimeException.class,
                ()-> commentService.creatComComment(namRegister, commentPostReq, nullBoard.getId()));

    }

    @Test
    @DisplayName("비회원 댓글 생성 실패")
    void failCreatComment() {
        //given
        UserDetailsImpl nullUser = new UserDetailsImpl(new User());

        //when
        Assertions.assertThrows(RuntimeException.class,
                ()-> commentService.creatComComment(nullUser, commentPostReq, namCommunityBoard.getId()));
    }

    @Test
    @DisplayName("작성자 불일치로 삭제 실패")
    void failDeleteComment() {
        //given
        UserDetailsImpl ayaRegister = new UserDetailsImpl(new User());
        when(boardRepository.findById(namCommunityBoard.getId())).thenReturn(Optional.of(namCommunityBoard));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->commentService.deleteComComment(ayaRegister, namComment.getId(), namCommunityBoard.getId()));
    }

    @Test
    @DisplayName("존재하지 않은 게시글로 댓글 삭제 실패")
    void failDeleteNoBoard() {
        //when && then
        assertThatThrownBy(()-> commentService.deleteComComment(namRegister, namComment.getId(), null))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글입니다");
    }

    @Test
    @DisplayName("존재하지 않은 댓글로 삭제 실패")
    void failDeleteNoComment() {
        //when && then
        assertThatThrownBy(()-> commentService.deleteComComment(namRegister, null, namCommunityBoard.getId()))
                .isInstanceOf(Exception.class);
    }
}