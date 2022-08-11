package com.wit.sullog.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wit.sullog.mapper.MainMapper;
import com.wit.sullog.model.AlcholRecord;
import com.wit.sullog.model.AlcholRecordResponse;

@Service
public class RecordService {
    
    private final MainMapper mainMapper;
    
    @Autowired
	public RecordService(MainMapper mainMapper) {this.mainMapper = mainMapper; }
    
    public List <AlcholRecordResponse> getRecordsByUserSeq(int user_seq){
    	return mainMapper.getRecordsByUserSeq(user_seq);
    }
    
    public int insertRecord(AlcholRecord record) {
		return mainMapper.insertRecord(record);
	}
    
    public int updateRecord(AlcholRecord record) {
    	return mainMapper.updateRecord(record);
    }
    
    public AlcholRecordResponse getRecordByUserSeqAndAlcholSeq(HashMap <String, Integer> info) {
    	return mainMapper.getRecordByUserSeqAndAlcholSeq(info);
    }
    
    public int deleteRecordByUserSeqAndAlcholSeq(HashMap <String, Integer> info) {
    	return mainMapper.deleteRecordByUserSeqAndAlcholSeq(info);
    }
    
    public List <String> getImageByAlcholSeq(int alchol_seq){
    	return mainMapper.getImageByAlcholSeq(alchol_seq);
    }

}