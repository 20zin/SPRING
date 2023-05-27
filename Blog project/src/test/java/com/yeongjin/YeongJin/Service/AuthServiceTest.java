package com.yeongjin.YeongJin.Service;

import com.yeongjin.YeongJin.Domain.User;
import com.yeongjin.YeongJin.Exception.AlreadyExistsEmailExeception;
import com.yeongjin.YeongJin.Repository.UserRepository;
import com.yeongjin.YeongJin.Request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test") //profile이 test인것만
@SpringBootTest
class AuthServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {userRepository.deleteAll();}

    @Test
    @DisplayName("회원가입 성공")
    void test1(){
        //given

//        PasswordEncoder encoder = new PasswordEncoder();

        Signup signup = Signup.builder()
                .password("1234")
                .email("oka022222@naver.com")
                .name("chelsea")
                .build();

        //when
        authService.signup(signup);

        //then
        assertEquals(1,userRepository.count());

        User user = userRepository.findAll().iterator().next();//현재있는 갯수에서 다음꺼 가져오기


        assertEquals("oka022222@naver.com",user.getEmail());
        assertNotNull(user.getPassword());
        assertEquals("1234",user.getPassword());
//        assertTrue(encoder.matches("1234", user.getPassword()));
        assertEquals("chelsea",user.getName());

    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2(){
        //given


        User user = User.builder()
                .email("oka022222@naver.com")
                .password("1234")
                .name("arsenal")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .password("1234")
                .email("oka022222@naver.com")
                .name("chelsea")
                .build();

        //expected
        assertThrows(AlreadyExistsEmailExeception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                authService.signup(signup);
            }
        });


        //then
        assertEquals(1,userRepository.count());

//        User user = userRepository.findAll().iterator().next();//현재있는 갯수에서 다음꺼 가져오기
//        assertEquals("oka022222@naver.com",user.getEmail());
//        assertEquals("1234",user.getPassword());
//        assertEquals("chelsea",user.getName());

    }

}