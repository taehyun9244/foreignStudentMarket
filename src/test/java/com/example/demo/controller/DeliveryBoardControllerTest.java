package com.example.demo.controller;

import com.example.demo.MockSpringSecurityFilter;
import com.example.demo.dto.reponse.DeliveryBoardDetailRes;
import com.example.demo.dto.reponse.DeliveryBoardSimRes;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.WebSecurityConfig;
import com.example.demo.service.DeliCommentService;
import com.example.demo.service.DeliveryBoardService;
import com.example.demo.service.UserService;
import com.example.demo.util.CountryEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.demo.util.CountryEnum.USA;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DeliveryBoardController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        })
@MockBean(JpaMetamodelMappingContext.class)
class DeliveryBoardControllerTest {

    private MockMvc mockMvc;
    private Principal mockPrincipal;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private DeliveryBoardService boardService;
    @MockBean
    private UserService userService;
    @MockBean
    private DeliCommentService commentService;

    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl namRegister;
    private DeliveryBoardPostReq namPostReq;
    private DeliveryBoard namDeliveryBoard;
    private Page<DeliveryBoardSimRes> boardSimResPage;
    private List<DeliveryBoardSimRes> simResList;
    private DeliveryBoardSimRes boardSimRes;
    private User namUser;
    private List<DeliComment> commentList;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        Address namAddress = new Address("Seoul", "Seocho", "132");
        namUser = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);
        namRegister =  new UserDetailsImpl(namUser);

        //namBoard
        namPostReq = new DeliveryBoardPostReq(
                "namTitle", "contents", "NewYork", USA, 1000);
        namDeliveryBoard =
                new DeliveryBoard(
                        1L, "제목", "배달", "뉴욕", USA,
                        "도쿄", 1000, 1, namUser, commentList
                );

        //comment
        DeliCommentPostReq commentPostReq = new DeliCommentPostReq("comment", 1L);
        DeliComment comment = new DeliComment(commentPostReq, namUser, namDeliveryBoard);
        commentList = new ArrayList<>();
        commentList.add(comment);

        //page
        boardSimRes = new DeliveryBoardSimRes(namDeliveryBoard);
        simResList = new ArrayList<>();
        simResList.add(boardSimRes);
        boardSimResPage = new PageImpl<>(simResList);


        mockPrincipal = new UsernamePasswordAuthenticationToken(namRegister, "", Collections.emptyList());
    }

    @Test
    @DisplayName("전체 운송 게시글 조회")
    void getBoardSim() throws Exception{

        when(boardService.getBoardSimV2(Pageable.ofSize(10))).thenReturn(boardSimResPage);

        this.mockMvc
                .perform(get("/deliveryBoards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageable", "0, 10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id").value(1L))
                .andExpect(jsonPath("$.content.[0].title").value(boardSimRes.getId()))
                .andExpect(jsonPath("$.content.[0].username").value(boardSimRes.getUsername()))
                .andExpect(jsonPath("$.content.[0].countComment").value(boardSimRes.getCountComment()))
                .andExpect(jsonPath("$.content.[0].price").value(boardSimRes.getPrice()))
                .andExpect(jsonPath("$.content.[0].from_city").value(boardSimRes.getFrom_city()))
                .andExpect(jsonPath("$.content.[0].from_country").value(boardSimRes.getFrom_country()))
                .andExpect(jsonPath("$.content.[0].createdAt").value(boardSimRes.getCreatedAt()))
                .andExpect(jsonPath("$.content.[0].updateAt").value(boardSimRes.getUpdateAt()));

        verify(boardService, times(1)).getBoardSimV2(Pageable.ofSize(10));
    }

    @Test
    @DisplayName("상세 운송 게시글 조회")
    void getBoardDetail() throws Exception{

        DeliveryBoardDetailRes detailRes = new DeliveryBoardDetailRes(namDeliveryBoard);
        List<DeliveryBoardDetailRes> deliveryBoardDetailResList = new ArrayList<>();
        deliveryBoardDetailResList.add(detailRes);

        when(boardService.getBoardDetailV2(namDeliveryBoard.getId())).thenReturn(deliveryBoardDetailResList);

        this.mockMvc
                .perform(get("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(namDeliveryBoard.getId()))
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

        verify(boardService, times(1)).getBoardDetailV2(namDeliveryBoard.getId());
    }


    @Test
    @DisplayName("게시글 생성")
    void creatDeliveryBoard() throws Exception{

        DeliveryBoardPostReq postReq =
                new DeliveryBoardPostReq(
                        "testTile", "testContent", "Tokyo", CountryEnum.JP, 1000
                );

        this.mockMvc
                .perform(get("/deliveryBoards")
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8").content(objectMapper.writeValueAsString(postReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 수정")
    void editDeliveryBoard() throws Exception{

        DeliveryBoardPostReq postReq =
                new DeliveryBoardPostReq(
                        "ayaTitle", "testContent", "Tokyo", CountryEnum.JP, 9999
                );

        this.mockMvc
                .perform(put("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .principal(mockPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8").content(objectMapper.writeValueAsString(postReq)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(boardService, times(1))
                .editDeliveryBoard(refEq(namDeliveryBoard.getId()), refEq(namRegister), refEq(postReq));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteDeliveryBoard() throws Exception{

        this.mockMvc
                .perform(delete("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());

        verify(boardService, times(1))
                .deleteDeliveryBoard(namDeliveryBoard.getId(), namRegister);
    }

    @Test
    @DisplayName("비회원으로 생성 불가")
    void failCreatBoard() throws Exception{

        doNothing().when(boardService).creatDeliveryBoard(namPostReq, userDetailsNull);

        this.mockMvc
                .perform(post("/deliveryBoards")
                .header("token", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(namPostReq)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Null 값으로 인해 생성 불가")
    void failCreatBoardNull() throws  Exception{

        DeliveryBoardPostReq deliveryBoardPostReq = new DeliveryBoardPostReq();

        this.mockMvc
                .perform(post("/deliveryBoards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(deliveryBoardPostReq))
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(
                        result -> Assertions.assertTrue(
                                result.getResolvedException() instanceof RuntimeException))
                .andReturn();
    }

    @Test
    @DisplayName("작성자 불일치(비회원)로 수정 불가")
    void failEditBoard() throws Exception{

        User ayaUser = new User("aya", "1234", "20220404", "123@naver.com",
                "010-1111-1111", new Address("도쿄", "시모키타자와", "123"));
        UserDetailsImpl aya = new UserDetailsImpl(ayaUser);
        Principal ayaPrincipal = new UsernamePasswordAuthenticationToken(aya, "", Collections.emptyList());

        this.mockMvc
                .perform(put("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .header(HttpHeaders.AUTHORIZATION, ayaPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("작성자 불일치(비회원)로 삭제 불가")
    void failDeleteBoard() throws Exception{

        User ayaUser = new User("aya", "1234", "20220404", "123@naver.com",
                "010-1111-1111", new Address("도쿄", "시모키타자와", "123"));
        UserDetailsImpl aya = new UserDetailsImpl(ayaUser);
        Principal ayaPrincipal = new UsernamePasswordAuthenticationToken(aya, "", Collections.emptyList());

        this.mockMvc
                .perform(delete("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .principal(ayaPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}