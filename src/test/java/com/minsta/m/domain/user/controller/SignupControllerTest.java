package com.minsta.m.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsta.m.domain.user.controller.data.request.SignupRequest;
import com.minsta.m.domain.user.entity.SmsAuthentication;
import com.minsta.m.domain.user.entity.User;
import com.minsta.m.domain.user.repository.SmsAuthRepository;
import com.minsta.m.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class SignupControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsAuthRepository smsAuthRepository;


    @BeforeEach
    @DisplayName("테스트전 유저 관련 데이터 추가")
    void createSampleData() {
        smsAuthRepository.deleteAll();
        userRepository.deleteAll();

        // SMS - success
        SmsAuthentication smsAuthentication = SmsAuthentication.builder()
                .phone("01037706712")
                .key(132884)
                .check(true)
                .build();

        SmsAuthentication conflict2 = SmsAuthentication.builder()
                .phone("010")
                .key(132884)
                .check(true)
                .build();

        SmsAuthentication notAuthenticated = SmsAuthentication.builder()
                .phone("0")
                .key(132884)
                .check(false)
                .build();

        User conflict = User.builder()
                .name("a")
                .nickName("중복")
                .phone("010")
                .password("password")
                .profileUrl("hi")
                .build();

        smsAuthRepository.save(smsAuthentication);
        smsAuthRepository.save(notAuthenticated);
        smsAuthRepository.save(conflict2);
        userRepository.save(conflict);
    }

    @Test
    @DisplayName("유저 signup - 성공")
    void success() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(
                "배선후",
                "닉네임",
                "password",
                "01037706712"
        );

        // When
        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signupRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("유저 signup - failed by not authenticated phone number")
    void failed_by_not_authenticated_phone_number() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(
                "하이",
                "닉넴",
                "password2",
                "0"
        );

        // When
        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signupRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("유저 signup - failed by duplicated_phone")
    void failed_by_duplicated_phone() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(
                "하이",
                "닉넴",
                "password2",
                "010"
        );

        // When
        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signupRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("유저 signup - failed by duplicated_nickName")
    void failed_by_duplicated_nick_name() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest(
                "하이",
                "중복",
                "password2",
                "01037706712"
        );

        // When
        mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signupRequest)))
                .andExpect(status().isConflict());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
