package com.wit.sullog.model;

import com.wit.sullog.StatusEnum;

import lombok.Data;

@Data
public class Message {

	private String code;

    private String result;
	
    private Object data;

    public Message() {
    	this.code = StatusEnum.OK.getStatusCode();
        this.data = null;
        this.result = "success";
    }

}
