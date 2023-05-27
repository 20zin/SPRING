package com.yeongjin.YeongJin.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * {
 *     "code" : "400"
 *     "message" : "잘못된 요청입니다"
 *     "validation" :{
 *         "title" : "값을 입력해주세요"
 *     }
 * }
 */
@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY) //빈 JSON데이터는 화면에 안보여줌
public class ErrorResponse {

    private final String code;
    private final String message;
    private Map<String,String> validation;

    @Builder
    public ErrorResponse(String code,String message,Map<String,String> validation){
        this.code = code;
        this.message = message;
        this.validation = validation != null? validation : new HashMap<>();
    }

    public void addvalidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

}
