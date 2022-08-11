package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLogout {
	// 	삭제 처리된 접근 토큰 값
    private String access_token;

    // 처리 결과가 성공이면 'success'가 리턴
    private String result;

    // 접근 토큰의 유효 기간(초 단위)
    private String expires_in;

    // 에러 코드
    private String error;

    // 에러 메세지
    private String error_description;
}
