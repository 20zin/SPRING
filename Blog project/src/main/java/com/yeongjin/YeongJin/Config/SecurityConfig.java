package com.yeongjin.YeongJin.Config;


import com.yeongjin.YeongJin.Domain.User;
import com.yeongjin.YeongJin.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean //로그인페이지도 필요없는 경로설정
    public WebSecurityCustomizer webSecurityCustomizer(){
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring()
                        .requestMatchers("/favicon.ico")
                        .requestMatchers("/error")
                        .requestMatchers(toH2Console());
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()//로그인페이지는 들어가야지...
                .requestMatchers("/auth/signup").permitAll()
                .requestMatchers("/admin").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .defaultSuccessUrl("/")
                .and()
                .rememberMe(rm ->rm.rememberMeParameter("remember")//자동로그인
                        .alwaysRemember(false)
                        .tokenValiditySeconds(2592000)//한달기억
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username)//User는 우리가 만든 엔티티
                        .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을수 없습니다"));

                return new UserPrincipal(user);

            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new SCryptPasswordEncoder(16,
                8,
                1,
                32,
                64);
    }


}
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails user= User.withUsername("chelsea")
//                .password("1234")
//                .roles("ADMIN")
//                .build();
//        manager.createUser(user);
//        return manager;

//NoOpPasswordEncoder.getInstance();//암호화하지는 않음