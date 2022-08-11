package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLogin {

	// ���� ��ū, �߱� �� expires_in �Ķ���Ϳ� ������ �ð�(��)�� ������ �����
    private String access_token;

    // ���� ��ū, ���� ��ū�� ����� ��� ���� ��ū�� �ٽ� �߱޹��� �� ���
    private String refresh_token;

    // ���� ��ū�� Ÿ������ Bearer�� MAC�� �� ������ ����
    private String token_type;

    // 	���� ��ū�� ��ȿ �Ⱓ(�� ����)
    private String expires_in;

    // ���� �ڵ�
    private String error;

    // ���� �޽���
    private String error_description;

}
