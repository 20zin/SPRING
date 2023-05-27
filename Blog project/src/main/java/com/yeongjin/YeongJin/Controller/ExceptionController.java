package com.yeongjin.YeongJin.Controller;

import com.yeongjin.YeongJin.Exception.InvalidRequest;
import com.yeongjin.YeongJin.Exception.YeongJinException;
import com.yeongjin.YeongJin.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
            ErrorResponse response = ErrorResponse.builder()
                    .code("400")
                    .message("잘못된 요청입니다.")
                    .build();

            for (FieldError fieldError : e.getFieldErrors()){
                response.addvalidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    @ResponseBody
    @ExceptionHandler(YeongJinException.class)
    public ResponseEntity<ErrorResponse> YeongjinException(YeongJinException e){
        int StatusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(StatusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        if(e instanceof InvalidRequest){
            InvalidRequest invalidRequest = (InvalidRequest) e;
            invalidRequest.getFieldName();
            invalidRequest.getMessage();
            body.addvalidation(invalidRequest.fieldName,invalidRequest.message);
        }

        ResponseEntity<ErrorResponse>response = ResponseEntity.status(StatusCode)
                .body(body);

        return response;
    }
}