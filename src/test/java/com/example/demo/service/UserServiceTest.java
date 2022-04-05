package com.example.demo.service;


import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Test
    @DisplayName("회원가입")
    void registerUserTest() {
        //given
        Long id = 1L;
        String username = "userA";
        String password = "123456789";
        String nickname = "nickName1";
        String birthday = "19920404";
        String email = "namtaehyun@naver.com";
        String phoneNumber = "010-1234-5678";
        String address = "seocho-gu";
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(id, username, password, birthday, nickname, email, phoneNumber, address);

        //when
        userService.registerUser(signUpRequestDto);

        //then
        Assertions.assertEquals(1L, signUpRequestDto.getId());
        Assertions.assertEquals("userA", signUpRequestDto.getUsername());
        Assertions.assertEquals("123456789", signUpRequestDto.getPassword());
        Assertions.assertEquals("19920404", signUpRequestDto.getBirthday());
        Assertions.assertEquals("nickName1", signUpRequestDto.getNickname());
        Assertions.assertEquals("010-1234-5678", signUpRequestDto.getPhoneNumber());
        Assertions.assertEquals("seocho-gu", signUpRequestDto.getAddress());
    }

    @Test
    @DisplayName("로그인")
    void createTokenTest() {
    }
}