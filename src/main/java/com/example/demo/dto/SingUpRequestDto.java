package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingUpRequestDto {
    private String username;
    private String password;
    private String birthday;
}
