package com.example.demo.service;

import com.example.demo.dto.reponse.MarketDetailRes;
import com.example.demo.dto.reponse.MarketSimRes;
import com.example.demo.dto.request.MarketPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.MarketBoard;
import com.example.demo.model.UploadFile;
import com.example.demo.model.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.UploadFileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
import com.example.demo.repository.queryRepository.JpqlBoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.util.FileStore;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MarketServiceTest {

    @InjectMocks
    private MarketService marketService;
    @InjectMocks
    private FileStore fileStore;
    @Mock
    private MarketRepository marketRepository;
    @Mock
    private JpqlBoardQueryRepository jpqlBoardQueryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AllBoardQueryRepository allBoardQueryRepository;
    @Mock
    private UploadFileRepository uploadFileRepository;

    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl namRegister;
    private UserDetailsImpl ayaRegister;
    private MarketBoard namMarketBoard;
    private MarketBoard ayaMaretBoard;
    private MarketPostReq marketPostReq;

    @BeforeEach
    public void setUp() {

        //User: null
        userDetailsNull = null;

        //User: Nam
        Address namAddress = new Address("Seoul", "Seocho", "132");
        User nam = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);
        namRegister = new UserDetailsImpl(nam);

        //MarketBoard: namBoard
        marketPostReq = new MarketPostReq("맥북16인치", "신형", "ET", "서울서초", 1000);
        namMarketBoard = new MarketBoard(marketPostReq, namRegister.getUser());

        //User: aya
        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        User aya = new User("aya", "1234", "20220528", "999@naver.com",
                "080-9999-9999", ayaAddress);
        ayaRegister = new UserDetailsImpl(aya);

        //MarketBoard: ayaBoard
        marketPostReq = new MarketPostReq("아이패드12.9", "중고", "ET", "도쿄시부야", 1000);
        ayaMaretBoard = new MarketBoard(marketPostReq, ayaRegister.getUser());

    }

    @Test
    @DisplayName("전체 게시글 조회 querydsl -> dto")
    void findAllBoard() {
        //given
        MarketSimRes marketSimRes1 = new MarketSimRes(namMarketBoard);
        MarketSimRes marketSimRes2 = new MarketSimRes(ayaMaretBoard);
        List<MarketSimRes> marketBoards = new ArrayList<>();
        marketBoards.add(marketSimRes1);
        marketBoards.add(marketSimRes2);

        Page<MarketSimRes> marketSimResPage = new PageImpl<>(marketBoards);

        //when
        when(allBoardQueryRepository.findMarketBoardAllDto(Pageable.ofSize(10))).thenReturn(marketSimResPage);
        Page<MarketSimRes> findAllBoard = marketService.getAllListMarketV2(Pageable.ofSize(10));

        //then
        assertThat(findAllBoard.getSize()).isEqualTo(2);
        assertThat(findAllBoard.getContent()).isEqualTo(marketBoards);
    }

    @Test
    @DisplayName("전체 게시글 조회 jpql")
    void findAllBoard2() {
        //given
        List<MarketBoard> marketBoards = new ArrayList<>();
        marketBoards.add(namMarketBoard);
        marketBoards.add(ayaMaretBoard);
        when(jpqlBoardQueryRepository.findAllMarket(0, 10)).thenReturn(marketBoards);

        //when
        List<MarketSimRes> allMarketBoards = marketService.getAllListMarket(0, 10);

        //then
        assertThat(allMarketBoards.size()).isEqualTo(2);
        assertThat(allMarketBoards.contains(namMarketBoard));
        assertThat(allMarketBoards.contains(ayaMaretBoard));
    }

    @Test
    @DisplayName("상세 게시글 조회")
    void findOneBoard() {
        //given
        MarketDetailRes marketDetailRes = new MarketDetailRes(namMarketBoard);
        List<MarketDetailRes> findMarketId = new ArrayList<>();
        findMarketId.add(marketDetailRes);

        when(allBoardQueryRepository.findByIdMarketBoardDto(namMarketBoard.getId())).thenReturn(findMarketId);

        //when
        List<MarketDetailRes> findOneBoard = marketService.getDetailMarketV2(namMarketBoard.getId());


        //then
        assertThat(findOneBoard.equals(namMarketBoard));

    }

    @Test
    @DisplayName("게시글 작성 성공")
    void creatBoard() {
        //given
        when(marketRepository.save(namMarketBoard)).thenReturn(namMarketBoard);

        //when
        MarketBoard saveBoard = marketRepository.save(namMarketBoard);

        //then
        assertThat(saveBoard.getItemName()).isEqualTo(namMarketBoard.getItemName());
        assertThat(saveBoard.getBody()).isEqualTo(namMarketBoard.getBody());
        assertThat(saveBoard.getCategory()).isEqualTo(namMarketBoard.getCategory());
        assertThat(saveBoard.getLocation()).isEqualTo(namMarketBoard.getLocation());
        assertThat(saveBoard.getPrice()).isEqualTo(namMarketBoard.getPrice());

    }

    @Test
    @DisplayName("게시글 수정 성공")
    void editBoard() throws IOException {
//        //given
//        List<UploadFile> uploadFiles = new ArrayList<>();
//        UploadFile uploadFile1 = new UploadFile("img1", "storeName1", namRegister.getUser(), namMarketBoard);
//        UploadFile uploadFile2 = new UploadFile("img2", "storeName2", namRegister.getUser(), namMarketBoard);
//        uploadFiles.add(uploadFile1);
//        uploadFiles.add(uploadFile2);
//
//        List<MultipartFile> multipartFiles = new ArrayList<>();
//
//        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));
//        given(marketRepository.findById(namMarketBoard.getId())).willReturn(Optional.ofNullable(namMarketBoard));
//        given(uploadFileRepository.saveAll(uploadFiles)).willReturn(uploadFiles);
//
//
//        MarketPostReq editMarketBoardReq =
//                new MarketPostReq("맥북13", "기스 있음", "ET", "부산", 1500);
//
//        //when
//        MarketBoard editMarketBoard =
//                marketService.editMarketBoard(namMarketBoard.getId(), namRegister, editMarketBoardReq, multipartFiles);
//
//        //then
//        assertThat(editMarketBoard.getItemName()).isEqualTo("맥북13");
//        assertThat(editMarketBoard.getBody()).isEqualTo("기스 있음");
//        assertThat(editMarketBoard.getCategory()).isEqualTo("ET");
//        assertThat(editMarketBoard.getLocation()).isEqualTo("부산");
//        assertThat(editMarketBoard.getPrice()).isEqualTo(1500);
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deleteBoard() {
        //given
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));
        when(marketRepository.findById(namMarketBoard.getId())).thenReturn(Optional.ofNullable(namMarketBoard));

        //when
        marketService.deleteMarketBoard(namMarketBoard.getId(), namRegister);

        //then
        verify(marketRepository, atLeastOnce()).delete(namMarketBoard);

    }

    @Test
    @DisplayName("상세 게시글 조회 실패")
    void findOneBoardFail() {
        //given
        MarketPostReq nullPost = new MarketPostReq(null, null, null, null, 0);
        MarketBoard saveMarket = new MarketBoard(nullPost, namRegister.getUser());

        //when && then
        try {
            marketService.getDetailMarketV2(saveMarket.getId());
        }catch (RuntimeException e){
            assertThat(e.getMessage()).isEqualTo("존재하지 않는 게시판입니다");
        }
    }

    @Test
    @DisplayName("작성자 불일치로 수정 실패")
    void editBoardNoUserFail() {
        //given
        MarketPostReq editMarketBoardReq =
                new MarketPostReq("맥북13", "기스 있음", "ET", "부산", 1500);
        List<MultipartFile> multipartFiles = new ArrayList<>();

        //when && then
        Assertions.assertThrows(RuntimeException.class,
                ()-> marketService.editMarketBoard(namMarketBoard.getId(), ayaRegister, editMarketBoardReq, multipartFiles));
    }

    @Test
    @DisplayName("비회원으로 수정 실패")
    void failEditNoUser(){
        //when && then
        List<MultipartFile> multipartFiles = new ArrayList<>();
        assertThatThrownBy(()-> marketService.editMarketBoard(namMarketBoard.getId(), null, marketPostReq, multipartFiles))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("게시글 존재하지 않아 수정 불가")
    void failEditNoBoard(){
        //when && then
        List<MultipartFile> multipartFiles = new ArrayList<>();
        assertThatThrownBy(()-> marketService.editMarketBoard(null, namRegister, marketPostReq, multipartFiles))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("작성자 불일치로 삭제 실패")
    void deleteBoardNoUserFail() {
        //when && then
        Assertions.assertThrows(RuntimeException.class,
                ()->marketService.deleteMarketBoard(namMarketBoard.getId(), ayaRegister));

    }

    @Test
    @DisplayName("비회원으로 게시글 작성 실패")
    void creatBoardNoUserFail() {
        //when & then
        Assertions.assertThrows(RuntimeException.class,
                ()->marketService.creatMarketBoard(marketPostReq, userDetailsNull));
    }

    @Test
    @DisplayName("게시글 존재하지 않아 삭제 실패")
    void failDeleteNoBoard() {
        //given
        given(userRepository.findByUsername(namRegister.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));

        //when && then
        assertThatThrownBy(()-> marketService.deleteMarketBoard(null, namRegister))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("존재하지 않는 게시글 입니다");
    }

    @Test
    @DisplayName("비회원으로 삭제 불가")
    void failDeleteNoUser() {
        //when && then
        assertThatThrownBy(()-> marketService.deleteMarketBoard(namMarketBoard.getId(), null))
                .isInstanceOf(Exception.class);
    }
}