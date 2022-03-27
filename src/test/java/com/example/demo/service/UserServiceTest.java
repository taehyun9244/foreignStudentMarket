package com.example.demo.service;


import com.example.demo.dto.SignUpRequestDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;


    @Test
    @DisplayName("회원가입")
    void registerUser(){
        //given
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
//        Optional<User> findUser = userRepository.findByUsername(username);
//        Optional<User> findNickname = userRepository.findByNickname(nickname);
//        Optional<User> findPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
//
//        //then
//        Assertions.assertThat(signUpRequestDto).isEqualTo(findUser);
//        Assertions.assertThat(signUpRequestDto).isEqualTo(findNickname);
//        Assertions.assertThat(signUpRequestDto).isEqualTo(findPhoneNumber);


    }
}