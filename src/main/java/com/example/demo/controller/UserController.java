package com.example.demo.controller;

import com.example.demo.dto.request.LoginReq;
import com.example.demo.dto.request.SignUpReq;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입 api
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignUpReq signUpRequestDto){
        userService.registerUser(signUpRequestDto);
    }

    //로그인 api
    @PostMapping("/user/login")
    public String createToken(@RequestBody LoginReq loginReq){
         return userService.createToken(loginReq);
    }
}
