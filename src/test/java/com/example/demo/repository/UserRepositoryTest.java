package com.example.demo.repository;

import com.example.demo.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("유저 저장 성공")
    void saveUser(){
        //given
        User user = new User("taehyun", "1234", "19920404", "namtaehyun@naver.com",
                "010-1111-1111", "seocho");

        //when
        User saveUser = userRepository.save(user);

        //then
        User findUser = userRepository.findById(saveUser.getId()).orElseThrow(
                ()-> new RuntimeException("존재 하지 않는 아이디입니다")
        );
        Assertions.assertThat(findUser).isEqualTo(saveUser);

    }

    @Test
    @DisplayName("유저 저장 실패")
    void saveUserFail(){
        //given
        User user = new User();
        //when
        userRepository.save(user);
        //then
        Optional<User> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }


    @Test
    @DisplayName("유저 찾기 성공")
    void findByUsername() {
        //given
        User user = new User("taehyun", "1234", "19920404", "namtaehyun@naver.com",
                "010-1111-1111", "seocho");
        //when
        User saveUser = userRepository.save(user);

        //then
        User findUser = userRepository.findByUsername(saveUser.getUsername()).orElseThrow(
                ()-> new RuntimeException("존재하는 유저가 없습니다")
        );
        Assertions.assertThat(findUser.getUsername()).isEqualTo(saveUser.getUsername());

    }

    @Test
    @DisplayName("유저의 전화번호 찾기 성공")
    void findByPhoneNumber() {

        //given
        User user = new User("taehyun", "1234", "19920404", "namtaehyun@naver.com",
                "010-1111-1111", "seocho");
        //when
        User saveUserPhone = userRepository.save(user);

        //then
        User findUser = userRepository.findByPhoneNumber(saveUserPhone.getPhoneNumber()).orElseThrow(
                ()-> new RuntimeException("등록되어 있는 번호가 없습니다")
        );
        Assertions.assertThat(findUser.getUsername()).isEqualTo(saveUserPhone.getUsername());
    }

    @Test
    @DisplayName("유저 전체 조회 성공")
    void findUserAll(){
        //given
        User user1 = new User("taehyun", "1234", "19920404", "namtaehyun@naver.com",
                "010-1111-1111", "seocho");
        User user2 = new User("ayako", "5678", "19970528", "ayako@naver.com",
                "010-2222-2222", "simokitazawa");

        userRepository.save(user1);
        userRepository.save(user2);

        //when
        List<User> result = userRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(user1, user2);
    }
}