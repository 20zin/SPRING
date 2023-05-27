package com.yeongjin.YeongJin.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeongjin.YeongJin.Repository.UserRepository;
import com.yeongjin.YeongJin.Request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectmapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    AuthControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
        //각각의 메소드들이 실행해주기전 먼저 실행함!
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("회원가입")
    void test6() throws Exception {
        //given
        Signup signup = Signup.builder()
                .password("1234")
                .email("oka022222@naver.com")
                .name("chelsea")
                .build();


        mockMvc.perform(post("/auth/signup")
                        .content(objectmapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}