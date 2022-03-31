package com.example.demo.service;

import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    //유저 회원가입
    public void registerUser(SignUpRequestDto signUpRequestDto){
        String username = signUpRequestDto.getUsername();
        String nickname = signUpRequestDto.getNickname();
        String birthday = signUpRequestDto.getBirthday();
        String email = signUpRequestDto.getEmail();
        String phoneNumber = signUpRequestDto.getPhoneNumber();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());

        Optional<User> foundUser = userRepository.findByUsername(username);
        Optional<User> foundNickname = userRepository.findByNickname(nickname);
        Optional<User> foundPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);

        if (foundUser.isPresent()){
            throw new RuntimeException("이미 등록된 아이디 입니다");
        }else if (foundNickname.isPresent()){
            throw new RuntimeException("이미 등록된 닉네임 입니다");
        }else if (foundPhoneNumber.isPresent()){
            throw new RuntimeException("이미 등록된 전화번호 입니다");
        }

        User user = new User(username, password, nickname, birthday, email, phoneNumber);
        userRepository.save(user);
    }

    public String createToken(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.findByUsername(signUpRequestDto.getUsername())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 유저입니다"));
        if (!passwordEncoder.matches(signUpRequestDto.getPassword(),user.getPassword())){
            throw new RuntimeException("잘못된 비밀번호입니다");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getId(), user.getNickname());
    }


}
