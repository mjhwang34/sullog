package com.wit.sullog.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AlcholRecordResponse {

	private int seq;
	private int user_seq;
	private int alchol_seq;
	private String taste;
	private String img_seq;
	private List<byte[]> image_byte;
	private String people;
	@JsonFormat(timezone="Asia/Seoul")
	private Timestamp time;
}
