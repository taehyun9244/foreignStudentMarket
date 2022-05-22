package com.example.demo.dto.request;

import com.example.demo.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String birthday;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private Address address;
}
