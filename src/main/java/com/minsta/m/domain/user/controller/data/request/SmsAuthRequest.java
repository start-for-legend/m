package com.minsta.m.domain.user.controller.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmsAuthRequest {

    @NotBlank(message = "phone is necessary valid")
    private String phone;

    @NotNull(message = "key is necessary valid")
    private int key;
}
