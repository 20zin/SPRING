package com.yeongjin.YeongJin.Exception;

/**
 * status -> 401
 */
public class Unauthorized extends YeongJinException {


    private static final String message = "인증이 필요합니다.";

    public Unauthorized() {
        super(message);
    }

        @Override
        public int getStatusCode() {
        return 401;

    }
}
