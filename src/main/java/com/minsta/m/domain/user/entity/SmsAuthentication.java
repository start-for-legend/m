package com.minsta.m.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SmsAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "auth_id", nullable = false)
    private Long authId;

    @Column(name = "phone_number", nullable = false, length = 11)
    private String phone;

    @Column(name = "authentication_key", nullable = false)
    private int key;

    @Column(name = "auth_check", nullable = false)
    private boolean check;

    public void setCheck(boolean check) {
        this.check = check;
    }
}
