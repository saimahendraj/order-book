package com.smj.order.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException{
    private HttpStatus errorCode;
    private String message;
    public CustomException(HttpStatus errorCode,String message){
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
