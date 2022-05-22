package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService mockUserService;

    private MockMvc mockMvc;


    @Test
    @DisplayName("회원가입 요청 API")
    void registerUser() {
        //given

        //when

        //then

    }


    @Test
    @DisplayName("로그인 시 JWT 토큰 생성 API")
    void createToken() {
        //given

        //when

        //then
    }
}