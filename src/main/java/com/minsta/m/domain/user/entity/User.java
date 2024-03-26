package com.minsta.m.domain.user.entity;

import com.minsta.m.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String name;

    @Column(name = "user_nick_name", nullable = false, unique = true, length = 20)
    private String nickName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phone;

    @Column(name = "profile_url")
    private String profileUrl;
}
