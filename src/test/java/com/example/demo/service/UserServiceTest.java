//package com.example.demo.service;
//
//
//import com.example.demo.dto.request.SignUpRequestDto;
//import com.example.demo.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class UserServiceTest {
//
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    @Autowired
//    UserServiceTest(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//
//    @Test
//    @DisplayName("회원가입")
//    void registerUser(){
//        //given
//        String username = "userA";
//        String password = "123456789";
//        String nickname = "nickName1";
//        String birthday = "19920404";
//        String email = "namtaehyun@naver.com";
//        String phoneNumber = "010-1234-5678";
//        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(username, password, nickname, birthday, email, phoneNumber);
//
//        //when
//        userService.registerUser(signUpRequestDto);
//
//
//        //then
////        Assertions.assertThat(signUpRequestDto).isEqualTo(userRepository);
//
//
//    }
//
////    @Test
////    @DisplayName("로그인")
////    String createToken(){
////
////    }
//}