package com.minsta.m.domain.user.controller.data.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "phone is necessary value")
    private String phone;

    @NotNull(message = "phone is necessary value")
    private String password;
}
