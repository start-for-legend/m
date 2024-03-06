package com.minsta.m.domain.user.controller.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendRequest {

    @NotBlank(message = "phone is necessary valid")
    private String phone;
}
