package com.wit.sullog.model;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class AlcholResponse {

	private int seq;
	private String name;
	private String type;
	private String abv;
	private String volume;
	private String price;
	private List <String> ingredients;
	private String manufacturer;
	private String owner;
	private HashMap <String, String> address;
	private String phone;

}
