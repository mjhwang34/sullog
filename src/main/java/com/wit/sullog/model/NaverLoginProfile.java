package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLoginProfile {

	// ������ �ĺ� ������ ���̹� ���̵𸶴� �����ϰ� �߱޵Ǵ� ���Դϴ�.
    private String id;

    // ����� ����
    private String nickname;

    // ����� �̸�
    private String name;

    // 	����� ���� �ּ�
    private String email;

    // ����
    private String gender;

    // ����� ���ɴ�
    private String age;

    // ����� ����(MM-DD ����)
    private String birthday;

    // ����� ������ ���� URL
    private String profile_image;

    // �������
    private String birthyear;

    // �޴���ȭ��ȣ
    private String mobile;

}
