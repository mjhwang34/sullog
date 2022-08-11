package com.wit.sullog.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AlcholRecord {

	private int seq;
	private int user_seq;
	private int alchol_seq;
	private String taste;
	private String img_seq;
	private String people;
	@JsonFormat(timezone="Asia/Seoul")
	private Timestamp time;

}
