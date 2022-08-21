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
	private String star;
	private String abv;
	private String incense;
	private String taste;
	private String texture;
	private String img_seq;
	@JsonFormat(timezone="Asia/Seoul")
	private Timestamp time;
	private String flower;
	private String fruit;
	private String grain;
	private String nut;
	private String sweetness;
	private String dairy;
	private String etc;
	private List<byte[]> image_byte;
}
