package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLoginProfileResponse {

	// API ȣ�� ��� �ڵ�
    private String resultcode;

    // ȣ�� ��� �޽���
    private String message;

    // Profile ��
    private NaverLoginProfile response;


}
