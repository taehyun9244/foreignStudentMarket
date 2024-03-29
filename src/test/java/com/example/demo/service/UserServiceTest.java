package com.example.demo.service;


import com.example.demo.dto.request.LoginReq;
import com.example.demo.dto.request.SignUpReq;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserRepository mockUserRepository;
    @Spy
    private PasswordEncoder mockPasswordEncoder;

    private SignUpReq namSignUpReq;
    private SignUpReq ayaSignUpReq;
    private SignUpReq doSignUpReq;
    private LoginReq loginReq;
    private UserDetailsImpl namRegister;

    @BeforeEach
    public void setUp(){
        Address namAddress = new Address("Seoul", "Seocho", "132");
        namSignUpReq = new SignUpReq("nam", mockPasswordEncoder.encode("1234"), "20220404",
                "123@naver.com", "010-1111-1111", namAddress);

        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        ayaSignUpReq = new SignUpReq("aya", mockPasswordEncoder.encode("1234"), "20220528",
                "999@naver.com", "080-9999-9999", ayaAddress);

        Address doAddress = new Address("NewYork", "BingHamton", "777");
        doSignUpReq = new SignUpReq("nam", mockPasswordEncoder.encode("1234"), "20220528",
                "999@naver.com", "080-9999-9999", doAddress);

        loginReq = new LoginReq("nam", mockPasswordEncoder.encode("1234"));

        User nam = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);
        namRegister = new UserDetailsImpl(nam);
    }

    @Test
    @DisplayName("회원가입 아이디 중복으로 실패")
    void registerUsernameDuplicateTest(){

        //given & when & then
        userService.registerUser(namSignUpReq);
        try {
            userService.registerUser(doSignUpReq);
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다 or 이미 등록되어 있는 아이디 입니다");
            fail();
        }

    }

    @Test
    @DisplayName("회원가입 핸드폰 번호 중복으로 실패")
    void registerDuplicatePhoneTest(){

        //given & when & then
        userService.registerUser(ayaSignUpReq);

        try {
            userService.registerUser(doSignUpReq);
        }catch (IllegalArgumentException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다 or 이미 등록되어 있는 번호입니다");
            fail();
        }
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerUserTest(){
       //given
        given(mockUserRepository.save(argThat(User -> User.getUsername().equals("nam"))))
                .willReturn(new User(namSignUpReq.getUsername(), namSignUpReq.getPassword(), namSignUpReq.getBirthday()
                        , namSignUpReq.getEmail(), namSignUpReq.getPhoneNumber(), namSignUpReq.getAddress()));

        //when
        userService.registerUser(namSignUpReq);

        //then
        verify(mockUserRepository).save(argThat(User -> User.getUsername().equals("nam")));
    }

    @Test
    @DisplayName("로그인 성공")
    void successLogin() {

    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인 실패")
    void wrongPassword() {
        //given
        LoginReq wrongPassword = new LoginReq("nam", mockPasswordEncoder.encode("1111"));
        given(mockUserRepository.findByUsername(loginReq.getUsername())).willReturn(Optional.ofNullable(namRegister.getUser()));

        //when
        assertThatThrownBy(()-> userService.createToken(wrongPassword))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("잘못된 비밀번호입니다");
    }

    @Test
    @DisplayName("틀린 아이디로 로그인 실패")
    void wrongUsername() {
        //given
        LoginReq wrongUsername = new LoginReq("aya", mockPasswordEncoder.encode("1234"));

        //when
        assertThatThrownBy(()-> userService.createToken(wrongUsername))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("가입되지 않은 유저입니다");
    }
}