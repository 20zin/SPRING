package com.yeongjin.YeongJin.Exception;

public class AlreadyExistsEmailExeception extends YeongJinException{

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailExeception() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

}
