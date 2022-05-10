package com.example.demo.controller;

import com.example.demo.dto.request.SignUpRequestDto;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 api
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignUpRequestDto signUpRequestDto){
        log.info("SignupRequestDto ={}", signUpRequestDto);
        userService.registerUser(signUpRequestDto);
    }

    //로그인 api
    @PostMapping("/user/login")
    public String createToken(@RequestParam String username,
                              @RequestParam String password){
        return userService.createToken(username, password);
    }


}
