package com.example.demo.controller;

import com.example.demo.dto.request.SignUpRequestDto;
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
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
//        mockMvc.perform()

    }

    @Test
    @DisplayName("아이디 잘못입력으로 JWT 토큰 생성 실패 API")
    void uncreatedTokenId() {

    }

    @Test
    @DisplayName("비밀번호 잘못입력으로 JWT 토큰 생성 실패 API")
    void uncreatedTokenPassword() {

    }

    @Test
    @DisplayName("로그인 시 JWT 토큰 생성 API")
    void createToken() {
    }
}