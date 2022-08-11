package com.wit.sullog.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wit.sullog.mapper.MainMapper;
import com.wit.sullog.model.Alchol;
import com.wit.sullog.model.AlcholResponse;

@Service
public class AlcholService {
	
	private final MainMapper mainMapper;

	@Autowired
	public AlcholService(MainMapper mainMapper) {this.mainMapper = mainMapper; }
	
	public Page <Alchol> getAlchols(HashMap <String, Integer> page_features){
		PageHelper.startPage(page_features.get("pageNum"), page_features.get("pageSize"));
		return (Page<Alchol>) mainMapper.getAlchols(page_features);
	}
	/*
	public List <Alchol> getAlchols(){
		return mainMapper.getAlchols();
	}*/
	
	public Page <Alchol> searchAlcholsByKeyword(HashMap <String, Object> info){
		PageHelper.startPage((int)info.get("pageNum"), (int)info.get("pageSize"));
		return (Page<Alchol>) mainMapper.searchAlcholsByKeyword(info);
	}
	/*
	public List <Alchol> searchAlcholsByKeyword(String keyword){
		return mainMapper.searchAlcholsByKeyword(keyword);
	}*/
	
	public Page <Alchol> getAlcholByType(HashMap <String, Object> info){
		PageHelper.startPage((int)info.get("pageNum"), (int)info.get("pageSize"));
		return (Page<Alchol>) mainMapper.getAlcholByType(info);
	}
	/*
	public List <Alchol> getAlcholByType(String type){
		return mainMapper.getAlcholByType(type);
	}*/
	
	public HashMap <String, List> formatAlcholResponse(List <Alchol> alchol_list) {
		HashMap <String, List> result = new HashMap <String, List>();
		List <AlcholResponse> data_list = new ArrayList<>();
		for(int i=0; i<alchol_list.size(); i++) {
			AlcholResponse alcholResponse = new AlcholResponse();
			List <String> ingredients = Arrays.asList(alchol_list.get(i).getIngredients().split(","));
			HashMap <String, String> address = new HashMap <String, String>();
			address.put("lat", alchol_list.get(i).getLat());
			address.put("lng", alchol_list.get(i).getLng());
			address.put("province", alchol_list.get(i).getProvince());
			address.put("city", alchol_list.get(i).getCity());
			
			alcholResponse.setSeq(alchol_list.get(i).getSeq());
			alcholResponse.setName(alchol_list.get(i).getName());
			alcholResponse.setType(alchol_list.get(i).getType());
			alcholResponse.setAbv(alchol_list.get(i).getAbv());
			alcholResponse.setVolume(alchol_list.get(i).getVolume());
			alcholResponse.setPrice(alchol_list.get(i).getPrice());
			alcholResponse.setIngredients(ingredients);
			alcholResponse.setManufacturer(alchol_list.get(i).getManufacturer());
			alcholResponse.setOwner(alchol_list.get(i).getOwner());
			alcholResponse.setAddress(address);
			alcholResponse.setPhone(alchol_list.get(i).getPhone());
			
			data_list.add(alcholResponse);
		}
		result.put("data", data_list);
		return result;
	}
	
	public Page <Alchol> test(HashMap <String, Integer> input){
		PageHelper.startPage(1, 10);
		return (Page<Alchol>) mainMapper.test(input);
	}

}
