package com.wit.sullog;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum StatusEnum {
	OK("200", "OK", HttpStatus.OK),
    UNAUTHORIZED("401", "UNAUTHORIZED", HttpStatus.UNAUTHORIZED)
    //LOGIN_FAIL("401", "LOGIN_FAIL", HttpStatus.UNAUTHORIZED),
    //DUPLICATED("001", "DUPLICATED", HttpStatus.BAD_REQUEST)
    ;

    String statusCode;
    String statusResult;
    HttpStatus httpStatus;

    StatusEnum(String statusCode, String statusResult, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.statusResult = statusResult;
        this.httpStatus = httpStatus;
    }
}