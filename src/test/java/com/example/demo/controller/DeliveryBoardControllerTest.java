package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Address;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.queryRepository.AllBoardQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliveryBoardService;
import com.example.demo.util.CountryEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = DeliveryBoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
class DeliveryBoardControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DeliveryBoardService boardService;
    @MockBean
    private AllBoardQueryRepository queryRepository;
    @MockBean
    private JpaQueryLookupStrategy jpaQueryLookupStrategy;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl namRegister;
    private DeliveryBoardPostReq namPostReq;
    private DeliveryBoard namDeliveryBoard;

    private List<DeliveryBoardSimRes> boardSimResList;
    private DeliveryBoardSimRes boardSimRes;
    private User namUser;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        CountryEnum namCountry = CountryEnum.USA;
        namPostReq = new DeliveryBoardPostReq("namTitle", "contents",
                "NewYork", namCountry, 1000);

        Address namAddress = new Address("Seoul", "Seocho", "132");
        namUser = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);

        namRegister =  new UserDetailsImpl(namUser);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());

        boardSimResList = new ArrayList<>();
        boardSimRes = new DeliveryBoardSimRes(namDeliveryBoard);
        boardSimResList.add(boardSimRes);

    }

    @Test
    @DisplayName("전체 운송 게시글 조회")
    void getBoardSim() throws Exception{
        when(boardService.getBoardSimV2(Pageable.ofSize(10))).thenReturn((Response) boardSimResList);

        this.mockMvc.perform(get("/deliveryBoards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("offset", "0")
                .param("limit", "10")
                .param("sort", "createdAt,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[0].id").value(boardSimRes.getId()))
                .andExpect(jsonPath("$..[0].title").value(boardSimRes.getTitle()))
                .andExpect(jsonPath("$..[0].username").value(boardSimRes.getUsername()))
                .andExpect(jsonPath("$..[0].price").value(boardSimRes.getPrice()))
                .andExpect(jsonPath("$..[0].from_city").value(boardSimRes.getFrom_city()))
                .andExpect(jsonPath("$..[0].from_country").value(boardSimRes.getFrom_country()))
                .andExpect(jsonPath("$..[0].createdAt").value(boardSimRes.getCreatedAt()))
                .andExpect(jsonPath("$..[0].updateAt").value(boardSimRes.getUpdateAt()));
    }

    @Test
    @DisplayName("상세 운송 게시글 조회")
    void getBoardDetail() throws Exception{

        DeliCommentPostReq postReq = new DeliCommentPostReq("comment", namDeliveryBoard.getId());
        DeliComment namComment = new DeliComment(postReq, namUser, namDeliveryBoard);
        List<DeliComment> commentList = new ArrayList<>();
        commentList.add(namComment);

        DeliveryBoardDetailRes boardDetailRes = new DeliveryBoardDetailRes(namDeliveryBoard);
        when(boardService.getBoardDetailV2(namDeliveryBoard.getId())).thenReturn(Collections.singletonList(boardDetailRes));

        this.mockMvc.perform(get("/deliveryBoards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(namDeliveryBoard.getId()))
                .andExpect(jsonPath("$.title").value(namDeliveryBoard.getTitle()))
                .andExpect(jsonPath("$.contents").value(namDeliveryBoard.getBody()))
                .andExpect(jsonPath("$.from_city").value(namDeliveryBoard.getFrom_city()))
                .andExpect(jsonPath("$.from_country").value(namDeliveryBoard.getFrom_country()))
                .andExpect(jsonPath("$.to_address").value(namDeliveryBoard.getUser().getAddress()))
                .andExpect(jsonPath("$.countComment").value(namDeliveryBoard.getCountComment()))
                .andExpect(jsonPath("$.price").value(namDeliveryBoard.getPrice()))
                .andExpect(jsonPath("$.username").value(namDeliveryBoard.getUser().getUsername()))
                .andExpect(jsonPath("$.createdAt").value(namDeliveryBoard.getCreatedAt()))
                .andExpect(jsonPath("$.updateAt").value(namDeliveryBoard.getUpdateAt()))
                .andExpect(jsonPath("$.comment").value(namDeliveryBoard.getDeliComments()));

    }

    @Test
    @DisplayName("게시글 생성")
    void creatDeliveryBoard() throws Exception{
    }

    @Test
    @DisplayName("게시글 수정")
    void editDeliveryBoard() throws Exception{
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteDeliveryBoard() throws Exception{
    }

    @Test
    @DisplayName("작성자 불일치로 수정 불가")
    void failEditBoard() throws Exception{
    }

    @Test
    @DisplayName("작성자 불일치로 삭제 불가")
    void failDeleteBoard() throws Exception{
    }
}