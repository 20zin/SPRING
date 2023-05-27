package com.yeongjin.YeongJin.Service;


import com.yeongjin.YeongJin.Domain.User;
import com.yeongjin.YeongJin.Exception.AlreadyExistsEmailExeception;
import com.yeongjin.YeongJin.Repository.UserRepository;
import com.yeongjin.YeongJin.Request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //private final PasswordEncoder passwordEncoder;
    //인터페이스로 상속하는게 2개 (plain,server) 어떤걸 꽂을까?

    public void signup(Signup signup) {
        //이메일중복체크
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
                if(userOptional.isPresent()){
                    throw new AlreadyExistsEmailExeception();
                }
                String encryptedPassword = passwordEncoder.encode(signup.getPassword());



        var user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
    }
}


//        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
//                .orElseThrow(() -> new InvalidSigninInformation());

//        Session session = user.addSession();