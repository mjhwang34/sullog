package com.wit.sullog.model;

import lombok.Data;

@Data
public class NaverLoginAuthorization {
	//���̹� �α��� ������ �����ϸ� ��ȯ�޴� ���� �ڵ�, ���� ��ū(access token) �߱޿� ���
	private String code;
	
	//����Ʈ �� ��û ���� ������ �����ϱ� ���� ���ø����̼ǿ��� ������ ���� ��ū���� URL ���ڵ��� ������ ��
	private String state;
	
	//���̹� �α��� ������ �����ϸ� ��ȯ�޴� ���� �ڵ�
	private String error;
	
	//���̹� �α��� ������ �����ϸ� ��ȯ�޴� ���� �޽���
	private String error_description;
}
