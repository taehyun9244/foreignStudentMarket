package com.example.demo.controller;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.dto.request.DeliveryBoardPostReq;
import com.example.demo.model.Address;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliCommentService;
import com.example.demo.util.CountryEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeliCommentControllerTest {

    private MockMvc mockMvc;
    private Principal mockPrincipal;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private DeliCommentService commentService;
    private UserDetailsImpl userDetailsNull;
    private UserDetailsImpl namRegister;
    private DeliveryBoardPostReq namPostReq;
    private DeliveryBoard namDeliveryBoard;
    private User namUser;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //User: null
        userDetailsNull = null;

        //User: Nam, namBoard: USA
        Address namAddress = new Address("Seoul", "Seocho", "132");
        namUser = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);
        namRegister =  new UserDetailsImpl(namUser);

        //namBoard
        CountryEnum namCountry = CountryEnum.USA;
        namPostReq = new DeliveryBoardPostReq("namTitle", "contents",
                "NewYork", namCountry, 1000);
        namDeliveryBoard = new DeliveryBoard(namPostReq, namRegister.getUser());

        mockPrincipal = new UsernamePasswordAuthenticationToken(namRegister, "", Collections.emptyList());

    }

    @Test
    @DisplayName("해당 게시글의 댓글 전체 조회")
    void findAllComment() throws Exception{

        DeliCommentPostReq postReq = new DeliCommentPostReq("comment", namDeliveryBoard.getId());
        DeliComment namComment = new DeliComment(postReq, namUser, namDeliveryBoard);
        DeliCommentRes deliCommentRes = new DeliCommentRes(namComment);
        List<DeliCommentRes> list = new ArrayList<>();
        list.add(deliCommentRes);
        Page<DeliCommentRes> page = new PageImpl(list);

        this.mockMvc.perform(get("/deliveryBoards/{deliveryBoardId}", namDeliveryBoard.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("offset", "0")
                .param("limit", "10")
                .param("sort", "createdAt,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(page.stream().findFirst().get().getId()));
    }

    @Test
    void creatDeliComment() {
    }

    @Test
    void deleteComment() {
    }
}