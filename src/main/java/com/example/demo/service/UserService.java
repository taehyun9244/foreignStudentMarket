package com.example.demo.service;

import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Address;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    //유저 회원가입
    public void registerUser(SignUpRequestDto signUpRequestDto){
        String username = signUpRequestDto.getUsername();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        String birthday = signUpRequestDto.getBirthday();
        String email = signUpRequestDto.getEmail();
        String phoneNumber = signUpRequestDto.getPhoneNumber();
        Address address = signUpRequestDto.getAddress();

        Optional<User> foundUser = userRepository.findByUsername(username);
        Optional<User> foundPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);

        if (foundUser.isPresent()){
            throw new RuntimeException("이미 존재하는 아이디입니다");
        }else if (foundPhoneNumber.isPresent()){
            throw new RuntimeException("이미 등록된 핸드폰 번호 입니다");
        }

        User user = new User(username, password, birthday, email, phoneNumber, address);
        userRepository.save(user);
    }

    //유저 로그인
    public String createToken(String username, String password ) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 유저입니다"));
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("잘못된 비밀번호입니다");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getId(), user.getPhoneNumber());
    }
}
