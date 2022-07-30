package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    @NotNull(message = "아이디를 입력해 주세요")
    private String username;

    @NotNull(message = "비밀번호를 입력해 주세요")
    private String password;
}
