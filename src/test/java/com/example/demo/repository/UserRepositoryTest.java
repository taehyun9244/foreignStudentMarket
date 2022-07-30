package com.example.demo.repository;

import com.example.demo.model.Address;
import com.example.demo.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User namUser;
    private User ayaUser;
    private User doUser;

    @BeforeEach
    public void setUp(){

        //User nam
        Address namAddress = new Address("Seoul", "Seocho", "132");
        namUser = new User("nam", "1234", "20220404", "123@naver.com",
                "010-1111-1111", namAddress);

        //User aya
        Address ayaAddress = new Address("Tokyo", "Sibuya", "155");
        ayaUser = new User("aya", "1234", "20220528", "999@naver.com",
                "080-9999-9999", ayaAddress);

        //User do
        Address doAddress = new Address("NewYork", "BingHamton", "777");
        doUser = new User("nam", "1234", "20220529", "777@naver.com",
                "080-9999-9999", doAddress);
    }

    @Test
    @DisplayName("유저 저장 성공")
    void saveUser(){

        //given
        User saveUser = userRepository.save(namUser);

        //when
        User findUser = userRepository.findByUsername(namUser.getUsername()).orElseThrow(
                ()-> new RuntimeException("존재 하지 않는 아이디입니다")
        );
        //then
        Assertions.assertThat(findUser.getUsername()).isEqualTo(saveUser.getUsername());
    }

    @Test
    @DisplayName("중복유저 아이디 회원가입 실패")
    void saveUserFail(){
        //given
        userRepository.save(namUser);

        //when & then
        try {
            userRepository.save(doUser);
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 등록되어 있는 아이디 입니다");
            fail();
        }    }
    
    @Test
    @DisplayName("중복유저 번호로 회원가입 실패")
    void findByDuplicatePhoneNumber() {

        //given
        userRepository.save(ayaUser);

        //when & then
        try {
            userRepository.save(doUser);
        }catch (IllegalArgumentException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 등록되어 있는 번호 입니다");
            fail();
        }
    }


    @Test
    @DisplayName("유저 찾기 성공")
    void findByUsername() {
        //given
        User saveUser = userRepository.save(namUser);

        //when
        User findUser = userRepository.findByUsername(saveUser.getUsername()).orElseThrow(
                ()-> new RuntimeException("존재하는 유저가 없습니다")
        );

        //then
        Assertions.assertThat(findUser.getUsername()).isEqualTo(saveUser.getUsername());
    }



    @Test
    @DisplayName("유저의 전화번호 찾기 성공")
    void findByPhoneNumber() {

        //given
        User saveUserPhone = userRepository.save(namUser);

        //when
        User findUser = userRepository.findByPhoneNumber(saveUserPhone.getPhoneNumber()).orElseThrow(
                ()-> new RuntimeException("등록되어 있는 번호가 없습니다")
        );

        //then
        Assertions.assertThat(findUser.getUsername()).isEqualTo(saveUserPhone.getUsername());
    }

    @Test
    @DisplayName("유저 전체 조회 성공")
    void findUserAll(){
        //given
        userRepository.save(namUser);
        userRepository.save(ayaUser);

        //when
        List<User> result = userRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(namUser, ayaUser);
    }
}