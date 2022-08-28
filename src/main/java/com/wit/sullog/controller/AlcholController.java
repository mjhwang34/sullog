package com.wit.sullog.controller;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wit.sullog.model.Alchol;
import com.wit.sullog.model.AlcholResponse;
import com.wit.sullog.model.Message;
import com.wit.sullog.service.AlcholService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/alchol")
public class AlcholController {

	@Autowired
	private AlcholService alcholService;

	@GetMapping("/test")
	public PageInfo<Alchol> test(){
		HashMap <String, Integer> input = new HashMap<>();
		input.put("pageNum", 1);
		input.put("pageSize", 10);
		return new PageInfo<Alchol>(alcholService.test(input));
	}
	
	@GetMapping("/getAlchols")
	public Message getAlchols(@RequestParam int pageNum, @RequestParam int pageSize) {
		System.out.println("********************getAlchols********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		HashMap <String, Integer> page_features = new HashMap<>();
		page_features.put("pageNum", pageNum);
		page_features.put("pageSize", pageSize);
		PageInfo <Alchol> alchol_page = new PageInfo<Alchol>(alcholService.getAlchols(page_features));
		Message message = new Message();
		message.setData(alchol_page);
		return message;
	}
	
	/*
	@GetMapping("/getAlchols")
	public Message getAlchols() {
		System.out.println("********************getAlchols********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		List <Alchol> alchol_list = new ArrayList <>();
		alchol_list = alcholService.getAlchols();
		HashMap <String, List> result = alcholService.formatAlcholResponse(alchol_list);
		Message message = new Message();
		message.setData(result.get("data"));
		return message;
	}*/
	
	@GetMapping("/searchAlchols")
	public Message searchAlchols(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String keyword) {
		System.out.println("********************searchAlchols********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("keyword: "+ keyword);
		HashMap <String, Object> info = new HashMap<>();
		info.put("pageNum", pageNum);
		info.put("pageSize", pageSize);
		info.put("keyword", keyword);
		PageInfo <Alchol> alchol_page = new PageInfo<Alchol>(alcholService.searchAlcholsByKeyword(info));
		Message message = new Message();
		message.setData(alchol_page);
		return message;
	}
	/*
	@GetMapping("/searchAlchols")
	public Message searchAlchols(@RequestParam String keyword) {
		System.out.println("********************searchAlchols********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("keyword: "+ keyword);
		List <Alchol> alchol_list = new ArrayList<>();
		alchol_list = alcholService.searchAlcholsByKeyword(keyword);
		HashMap <String, List> result = alcholService.formatAlcholResponse(alchol_list);
		Message message = new Message();
		message.setData(result.get("data"));
		return message;
	}*/
	
	@GetMapping("/getAlcholByType")
	public Message getAlcholByType(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String type) {
		System.out.println("********************getAlcholByType********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("type: "+ type);
		HashMap <String, Object> info = new HashMap<>();
		info.put("pageNum", pageNum);
		info.put("pageSize", pageSize);
		info.put("type", type);
		PageInfo <Alchol> alchol_page = new PageInfo<Alchol>(alcholService.getAlcholByType(info));
		Message message = new Message();
		message.setData(alchol_page);
		return message;
	}
	
	/*
	@GetMapping("/getAlcholByType")
	public Message getAlcholByType(@RequestParam String type) {
		System.out.println("********************getAlcholByType********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("type: "+ type);
		List <Alchol> alchol_list = new ArrayList<>();
		alchol_list = alcholService.getAlcholByType(type);
		HashMap <String, List> result = alcholService.formatAlcholResponse(alchol_list);
		Message message = new Message();
		message.setData(result.get("data"));
		return message;
	}*/
}
