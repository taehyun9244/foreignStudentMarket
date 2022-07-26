package com.example.demo.service;

import com.example.demo.dto.request.ComBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CommunityServiceTest {

    @InjectMocks
    private CommunityService communityService;

    @Mock
    private CommunityRepository communityRepository;

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
    @DisplayName("커뮤니티 게시글 전체 조회")
    void getCommunityBoard() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("커뮤니티 게시글 상세 조회")
    void getComBoardDetail() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("커뮤니티 게시글 작성 ")
    void postComBoard() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정")
    void editCommunityBoard() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제")
    void deleteComBoard() {
        //given

        //when

        //then
    }
}