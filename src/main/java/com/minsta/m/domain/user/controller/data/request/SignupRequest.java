package com.minsta.m.domain.user.controller.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "name is necessary valid")
    private String name;

    @NotBlank(message = "nickName is necessary valid")
    private String nickName;

    @NotBlank(message = "password is necessary valid")
    private String password;

    @NotBlank(message = "phone_number is necessary valid")
    private String phone;
}
