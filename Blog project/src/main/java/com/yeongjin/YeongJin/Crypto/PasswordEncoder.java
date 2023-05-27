package com.yeongjin.YeongJin.Crypto;

public interface PasswordEncoder {

    String encrypt(String rawPassword);
    boolean matches(String rawPassword, String encryptedPassword);
}
