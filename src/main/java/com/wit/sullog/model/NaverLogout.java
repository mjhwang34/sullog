package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLogout {
	// 	���� ó���� ���� ��ū ��
    private String access_token;

    // ó�� ����� �����̸� 'success'�� ����
    private String result;

    // ���� ��ū�� ��ȿ �Ⱓ(�� ����)
    private String expires_in;

    // ���� �ڵ�
    private String error;

    // ���� �޼���
    private String error_description;
}
