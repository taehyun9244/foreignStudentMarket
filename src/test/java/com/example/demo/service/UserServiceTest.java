//package com.example.demo.service;
//
//
//import com.example.demo.dto.SignUpRequestDto;
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//class UserServiceTest {
//
//    UserService userService;
//    UserRepository userRepository;
//
//
//    @Test
//    void registerUser() {
//        //given
//        Long id = 1L;
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
//
//        //then
//        Assertions.assertThat(signUpRequestDto).isEqualTo(findUser);
//
//
//    }
//}