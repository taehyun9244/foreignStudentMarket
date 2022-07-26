package com.example.demo.dto.request;

import com.example.demo.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReq {
    @NotBlank
    @Size(min = 2, message = "최소 2글자 이상으로 지어주세요")
    private String username;
    @NotBlank
    @Size(min = 8, message = "최소 8글자 이상으로 지어주세요")
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
