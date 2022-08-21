package com.wit.sullog.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.wit.sullog.model.Alchol;
import com.wit.sullog.model.AlcholRecord;
import com.wit.sullog.model.AlcholRecordResponse;
import com.wit.sullog.model.User;

@Mapper
public interface MainMapper {
	int insertUser(HashMap <String, String> loginInfo);
	User getUserInfoByEmail(HashMap <String, String> info);
	
	List <AlcholRecordResponse> getRecordsByUserSeq(int user_seq);
	int insertRecord(AlcholRecord record);
	int updateRecord(AlcholRecord record);
	AlcholRecordResponse getRecordByUserSeqAndAlcholSeq(HashMap <String, Integer> info);
	int deleteRecordByUserSeqAndAlcholSeq(HashMap <String, Integer> info);
	List <String> getImageByAlcholSeq(int alchol_seq);
	List <AlcholRecord> searchRecordByKeyword(HashMap <String, Object> info);
	
	Page <Alchol> getAlchols(HashMap <String, Integer> page_features);
	//List <Alchol> getAlchols();
	Page <Alchol> searchAlcholsByKeyword(HashMap <String, Object> info);
	//List <Alchol> searchAlcholsByKeyword(String keyword);
	Page <Alchol> getAlcholByType(HashMap <String, Object> info);
	//List <Alchol> getAlcholByType(String type);
	
	Page <Alchol> test(HashMap <String, Integer> input);
}
