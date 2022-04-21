package com.example.demo.service;


import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;


    @Test
    @DisplayName("회원가입 아이디 중복으로 실패")
    void registerUsernameDuplicateTest(){
        //given
        SignUpRequestDto signUpRequestFailDto1 = new SignUpRequestDto("username", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "seocho");
        SignUpRequestDto signUpRequestFailDto2 = new SignUpRequestDto("username", mockPasswordEncoder.encode("1234"),
                "19970528", "ayako@naver.com", "010-2222-2222", "sibuya");

        //when
        userService.registerUser(signUpRequestFailDto1);

        //then
        try {
            userService.registerUser(signUpRequestFailDto2);
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디 입니다");
        }
    }

    @Test
    @DisplayName("회원가입 핸드폰 번호 중복으로 실패")
    void registerDuplicatePhoneTest(){
        //given
        SignUpRequestDto signUpRequestFailDto1 = new SignUpRequestDto("usernameA", mockPasswordEncoder.encode("1234"),
                "19970528", "ayako@naver.com", "010-1111-1111", "sibuya");
        SignUpRequestDto signUpRequestFailDto2 = new SignUpRequestDto("usernameB", mockPasswordEncoder.encode("5678"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "seocho");

        //when
        userService.registerUser(signUpRequestFailDto1);

        //then
        try {
            userService.registerUser(signUpRequestFailDto2);
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다 or 이미 등록되어 있는 번호입니다");
        }
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerUserTest(){
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("username", mockPasswordEncoder.encode("1234"),
                "19920404", "namtaehyun@naver.com", "010-1111-1111", "seocho");
        given(mockUserRepository.findById(any())).willReturn(Optional.empty());
        given(mockUserRepository.save(argThat(User -> User.getUsername().equals("username"))))
                .willReturn(new User(signUpRequestDto.getUsername(), signUpRequestDto.getPassword(), signUpRequestDto.getBirthday()
                        , signUpRequestDto.getEmail(), signUpRequestDto.getPhoneNumber(), signUpRequestDto.getAddress()));

        //when
        userService.registerUser(signUpRequestDto);

        //then
        verify(mockUserRepository).save(argThat(User -> User.getUsername().equals("username")));
    }


}