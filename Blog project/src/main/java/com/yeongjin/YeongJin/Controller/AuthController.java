package com.yeongjin.YeongJin.Controller;

import com.yeongjin.YeongJin.Config.AppConfig;
import com.yeongjin.YeongJin.Request.Signup;
import com.yeongjin.YeongJin.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j //log
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    private final AppConfig appConfig;

    @GetMapping("/auth/login")
    public String login(){
        return "로그인 페이지입니다";
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }








}


//        ResponseCookie cookie = ResponseCookie.from("SESSION",accesstoken)
//                .domain("localhost") //todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .secure(false)
//                .maxAge(Duration.ofDays(30))//쿠키유지시간
//                .sameSite("Strict")
//                .build();
//
//        log.info(">>>>>>>>>>>cookie={}", cookie.toString());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE,cookie.toString())
//                .build();

//    SecretKey key = Jwts.SIG.HS256.keyBuilder().build();
//    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();

//    byte[] encodedKey = key.getEncoded();//byte
//    String strKey = Base64.getEncoder().encodeToString(encodedKey);//인코딩