package com.minsta.m.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //USER
    NOT_AUTHENTICATION_PHONE("Not Authenticated phone number, retrying after authenticate", 403),
    AUTH_KEY_IS_NOT_MATCHED("Auth Key is not matched", 403),
    USER_PHONE_EXIST("Duplicated phone value", 409),
    USER_NICK_NAME_EXIST("Duplicated nickName value", 409),

    //SERVER
    SERVER_ERROR("SERVER ERROR", 500);


    private final String message;
    private final int status;
}
