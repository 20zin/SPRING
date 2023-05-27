package com.yeongjin.YeongJin.Exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public abstract class YeongJinException extends RuntimeException{

    private final Map<String,String> validation = new HashMap<>();


    public YeongJinException(String message) {
        super(message);
    }

    public YeongJinException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }
}
