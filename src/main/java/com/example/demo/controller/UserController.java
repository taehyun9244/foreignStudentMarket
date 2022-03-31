package com.example.demo.controller;

import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 api
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignUpRequestDto signUpRequestDto){
        userService.registerUser(signUpRequestDto);
    }
    //로그인 api
    @PostMapping("/user/login")
    public String createToken(@RequestBody SignUpRequestDto signUpRequestDto){
        return userService.createToken(signUpRequestDto);
    }




}
