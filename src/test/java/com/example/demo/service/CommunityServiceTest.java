package com.example.demo.service;

import com.example.demo.dto.reponse.ComBoardDetailRes;
import com.example.demo.dto.reponse.ComBoardSimRes;
import com.example.demo.dto.request.ComBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
import com.example.demo.repository.queryRepository.JpqlBoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CommunityServiceTest {

    @InjectMocks
    private CommunityService communityService;
    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private AllBoardQueryRepository boardQueryRepository;
    @Mock
    private JpqlBoardQueryRepository jpqlBoardQueryRepository;
    @Mock
    private UserRepository userRepository;

    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private UserDetailsImpl userDetailsNull;

    private CommunityBoard namCommunityBoard;
    private CommunityBoard ayaCommunityBoard;
    private ComBoardPostReq namPostReq;
    private ComBoardPostReq ayaPostReq;

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

        //User: aya, ayaBoard: Sinsa3dong
        ayaPostReq = new ComBoardPostReq("ayaTitle", "ayaSubTitle", "ayaContents", "Sinsa3dong");
        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com", "080-9999-9999", ayaAddress);
        ayaRegister = new UserDetailsImpl(aya);
        ayaCommunityBoard = new CommunityBoard(ayaPostReq, ayaRegister.getUser());
    }

    @Test
    @DisplayName("커뮤니티 게시글 전체 조회 querydsl")
    void getCommunityBoard() {
        //given
        ComBoardSimRes comBoardSimRes1 = new ComBoardSimRes(namCommunityBoard);
        ComBoardSimRes comBoardSimRes2 = new ComBoardSimRes(ayaCommunityBoard);
        List<ComBoardSimRes> communityBoards = new ArrayList<>();
        communityBoards.add(comBoardSimRes1);
        communityBoards.add(comBoardSimRes2);

        Page<ComBoardSimRes> communityBoardPage = new PageImpl<>(communityBoards);

        when(boardQueryRepository.findCommunityBoardAllDto(Pageable.ofSize(10))).thenReturn(communityBoardPage);

        //when
        Page<ComBoardSimRes> findAllBoard = communityService.getCommunityBoardV2(Pageable.ofSize(10));

        //then
        assertThat(findAllBoard.getSize()).isEqualTo(2);
        assertThat(findAllBoard.getContent()).isEqualTo(communityBoards);
    }

    @Test
    @DisplayName("커뮤니티 게시글 전체 조회 jpql")
    void findAllBoardJpql() {
        //given
        List<CommunityBoard> communityBoards = new ArrayList<>();
        communityBoards.add(namCommunityBoard);
        communityBoards.add(ayaCommunityBoard);

        given(jpqlBoardQueryRepository.findAllComBoard(0, 10)).willReturn(communityBoards);

        //when
        List<ComBoardSimRes> communityBoardList = communityService.getCommunityBoard(0, 10);

        //then
        assertThat(communityBoardList.size()).isEqualTo(2);
        assertThat(communityBoardList.contains(namCommunityBoard));
        assertThat(communityBoardList.contains(ayaCommunityBoard));

    }

    @Test
    @DisplayName("커뮤니티 게시글 상세 조회")
    void getComBoardDetail() {
        //given
        ComBoardDetailRes comBoardDetailRes = new ComBoardDetailRes(namCommunityBoard);

        List<ComBoardDetailRes> findById = new ArrayList<>();
        findById.add(comBoardDetailRes);

        when(boardQueryRepository.findByCommunityBoardIdDto(namCommunityBoard.getId())).thenReturn(findById);

        //when
        List<ComBoardDetailRes> findByIdBoard = communityService.getComBoardDetailV2(namCommunityBoard.getId());

        //then
        assertThat(findByIdBoard).isEqualTo(findById);
    }

    @Test
    @DisplayName("커뮤니티 게시글 작성")
    void postComBoard() {
        //given
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));

        //when
        communityService.postComBoard(namPostReq, namRegister);

        //then
        verify(communityRepository, times(0)).save(namCommunityBoard);
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정")
    void editCommunityBoard() {
        //given
        ComBoardPostReq editBoard = new ComBoardPostReq("제목", "서브제목", "내용", "위치");

        when(communityRepository.findById(namCommunityBoard.getId())).thenReturn(Optional.ofNullable(namCommunityBoard));

        //when
        communityService.editCommunityBoard(namCommunityBoard.getId(), editBoard, namRegister);

        //then
        assertThat(namCommunityBoard.getTitle().equals("제목"));
        assertThat(namCommunityBoard.getSubtitle().equals("서브제목"));
        assertThat(namCommunityBoard.getBody().equals("내용"));
        assertThat(namCommunityBoard.getLocation().equals("위치"));
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제")
    void deleteComBoard() {
        //given
        when(communityRepository.findById(namCommunityBoard.getId())).thenReturn(Optional.ofNullable(namCommunityBoard));

        //when
        communityService.deleteComBoard(namCommunityBoard.getId(), namRegister);

        //then
        verify(communityRepository, atLeastOnce()).delete(namCommunityBoard);
    }

    @Test
    @DisplayName("비회원 게시판 작성 실패")
    void failCreatBoard() {
        //given, when, then
        Assertions.assertThrows(RuntimeException.class,
                ()->communityService.postComBoard(namPostReq, userDetailsNull));
    }

    @Test
    @DisplayName("게시글 삭제로 상세 조회 실패")
    void failFindDetailBoard() {
        //given
        ComBoardPostReq nullPost = new ComBoardPostReq(null, null, null, null);
        CommunityBoard saveCommunity = new CommunityBoard(nullPost, namRegister.getUser());

        //when & then
        try {
            communityService.getComBoardDetailV2(saveCommunity.getId());
        }catch (RuntimeException e){
            assertThat(e.getMessage()).isEqualTo("존재하지 않는 게시판입니다");
        }
    }

    @Test
    @DisplayName("게시판 작성자 불일치로 수정 실패")
    void failEditBoard() {
        //given
        Long namBoardId = namCommunityBoard.getId();
        when(communityRepository.findById(namBoardId)).thenReturn(Optional.of(namCommunityBoard));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->communityService.editCommunityBoard(namBoardId, ayaPostReq, ayaRegister));
    }

    @Test
    @DisplayName("게시판 작성자 불일치로 삭제 실패")
    void failDeleteBoard() {
        //given
        when(communityRepository.findById(namCommunityBoard.getId())).thenReturn(Optional.of(namCommunityBoard));

        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->communityService.deleteComBoard(namCommunityBoard.getId(), ayaRegister));
    }

    @Test
    @DisplayName("게시글 존재하지 않아 수정 실패")
    void failEditNoBoard() {
        //when && then
        assertThatThrownBy(()-> communityService.editCommunityBoard(null, ayaPostReq, ayaRegister))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않은 게시글입니다");

    }

    @Test
    @DisplayName("게시글 존재하지 않아 삭제 실패")
    void failDeleteNoBoard() {
        //when && then
        assertThatThrownBy(()-> communityService.deleteComBoard(null, ayaRegister))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("게시글이 존재하지 않습니다");
    }
}