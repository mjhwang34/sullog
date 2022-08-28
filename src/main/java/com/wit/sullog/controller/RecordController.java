package com.wit.sullog.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wit.sullog.model.AlcholRecord;
import com.wit.sullog.model.AlcholRecordResponse;
import com.wit.sullog.model.AlcholRecordResponse1;
import com.wit.sullog.model.AlcholRecordResponse2;
import com.wit.sullog.model.Message;
import com.wit.sullog.service.RecordService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private RecordService recordService;
	
	//private static String image_folder_path = "C:\\sullog\\image\\";
	//private static String image_folder_path= "/Users/minjoo/Documents/";
	private static String image_folder_path = "/var/lib/tomcat9/webapps/images/";
	
	@GetMapping("/getRecords")
	public Message getRecordsByUserSeq(@RequestParam int user_seq) {
		System.out.println("********************getRecords********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("user_seq: "+ user_seq);
		List <AlcholRecordResponse2> record_list = new ArrayList <>();
		record_list = recordService.getRecordsByUserSeq(user_seq);
		for(int i=0; i<record_list.size(); i++) {
			if(record_list.get(i).getImg_seq()==null) continue;
			String file_name_string = record_list.get(i).getImg_seq();
			List file_name_list = fileNameStringToList(file_name_string);
			List image_byte = new ArrayList <>();
			for(int j=0; j<file_name_list.size(); j++) {
				byte[] image_to_byte = null;
				try {
					image_to_byte = getImgByteArray(file_name_list.get(j).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				image_byte.add(image_to_byte);
			}
			record_list.get(i).setImage_byte(image_byte);
		}
		Message message = new Message();
		message.setData(record_list);
		return message;
	}
	
	@GetMapping("/getRecord")
	public Message getRecordByUserSeqAndAlcholSeq(@RequestParam int user_seq, @RequestParam int alchol_seq) {
		System.out.println("********************getRecord********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("user_seq: "+ user_seq+" alchol_seq: "+alchol_seq);
		HashMap <String, Integer> info = new HashMap<String, Integer>();
		info.put("user_seq", user_seq);
		info.put("alchol_seq", alchol_seq);
		AlcholRecordResponse2 record_response = recordService.getRecordByUserSeqAndAlcholSeq(info);
		String file_name_string = record_response.getImg_seq();
		if(file_name_string!=null) {
			List file_name_list = fileNameStringToList(file_name_string);
			List image_byte = new ArrayList <>();
			for(int j=0; j<file_name_list.size(); j++) {
				byte[] image_to_byte = null;
				try {
					image_to_byte = getImgByteArray(file_name_list.get(j).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				image_byte.add(image_to_byte);
			}
			record_response.setImage_byte(image_byte);
		}
		Message message = new Message();
		message.setData(record_response);
		return message;
	}
	
	@PutMapping("/updateRecord")
	public Message updateRecord(@RequestBody AlcholRecord record) {
		System.out.println("********************getRecord********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("record: "+ record);
		Message message = new Message();
		HashMap <String, Integer> result = new HashMap <String, Integer>();
		HashMap <String, Integer> info = new HashMap<>();
		info.put("user_seq", record.getUser_seq());
		info.put("alchol_seq", record.getAlchol_seq());
		AlcholRecordResponse2 foundRecord2 = null;
		foundRecord2 = recordService.getRecordByUserSeqAndAlcholSeq(info);
		int res=-1;
		try {
			if(foundRecord2==null) { //추가
				System.out.println("insert");
				res = recordService.insertRecord(record);
			}
			else { //업데이트
				System.out.println("update");
				if(foundRecord2.getImg_seq()!=null) {
					record.setImg_seq(foundRecord2.getImg_seq());
				}
				res = recordService.updateRecord(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			if(res==-1) {
				message.setCode("003");
				message.setResult("update failed");
				return message;
			}
		}
		result.put("updated_rows", res);
		message.setData(result);
		return message;
	}
	
	@DeleteMapping("/deleteRecord")
	public Message deleteRecord(@RequestParam int user_seq, @RequestParam int alchol_seq) {
		System.out.println("********************deleteRecord********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("user_seq: "+ user_seq+" alchol_seq: "+alchol_seq);
		Message message = new Message();
		HashMap <String, Integer> info = new HashMap<String, Integer>();
		info.put("user_seq", user_seq);
		info.put("alchol_seq", alchol_seq);
		int res = -1;
		try {
			info.put("user_seq", user_seq);
			info.put("alchol_seq", alchol_seq);
			AlcholRecordResponse2 alcholRecord2 = recordService.getRecordByUserSeqAndAlcholSeq(info);
			res = recordService.deleteRecordByUserSeqAndAlcholSeq(info);
			List file_name_list = fileNameStringToList(alcholRecord2.getImg_seq());
			deleteImg(file_name_list);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(res==-1) {
				message.setCode("004");
				message.setData("delete failed");
				return message;
			}
		}
		HashMap <String, Integer> result = new HashMap <String, Integer>();
		result.put("deleted_rows", res);
		message.setData(result);
		return message;
	}

	@PostMapping("/imageUpload")
    public Message imageUpload(@RequestParam List<MultipartFile> files, @RequestParam int user_seq, @RequestParam int alchol_seq) throws IOException {
		System.out.println("********************imageUpload********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("user_seq: "+ user_seq+" alchol_seq: "+alchol_seq);
		Message message = new Message();
		List <String> file_name_list = new ArrayList <>();
        for (int i = 0; i < files.size(); i++) {
        	System.out.println(files.get(i).getOriginalFilename());
        	if (!files.get(i).isEmpty()) {
        		String uuid = UUID.randomUUID().toString();
        		String fullPath = image_folder_path +uuid+".jpg";
        		System.out.println("파일 저장 fullPath: "+ fullPath);
        		files.get(i).transferTo(new File(fullPath));
        		file_name_list.add(uuid);
        	}
        }
        String file_name_string = String.join(",", file_name_list);
        HashMap <String, Integer> info = new HashMap<>();
        info.put("user_seq", user_seq);
		info.put("alchol_seq", alchol_seq);
		AlcholRecordResponse2 foundRecord2 = null;
		foundRecord2 = recordService.getRecordByUserSeqAndAlcholSeq(info);
		AlcholRecord record = new AlcholRecord();
		int res=-1;
		try {
			if(foundRecord2==null) { //추가
				System.out.println("insert");
				record.setAlchol_seq(alchol_seq);
				record.setUser_seq(user_seq);
				record.setImg_seq(file_name_string);
				res = recordService.insertRecord(record);
			}
			else { //업데이트
				System.out.println("update");
				record = setAlcholRecord(foundRecord2, file_name_string);
				res = recordService.updateRecord(record);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			if(res==-1) {
				message.setCode("003");
				message.setResult("update failed");
				return message;
			}
		}
		String resultMessage = Integer.toString(file_name_list.size()) + " images saved";
		message.setData(resultMessage);
        return message;
    }
	
	@GetMapping("getImage")
	public Message getImageByAlcholSeq(@RequestParam int alchol_seq) {
		System.out.println("********************getImage********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("alchol_seq: "+alchol_seq);
		List <String> file_name_list = new ArrayList <>();
		file_name_list = recordService.getImageByAlcholSeq(alchol_seq);
		List <String> total_file_name_list = new ArrayList <>(); 
		for(int i=0; i<file_name_list.size(); i++) {
			String temp = file_name_list.get(i);
			List <String> cur_file_name_list = fileNameStringToList(temp);
			for(int j=0; j<cur_file_name_list.size(); j++) {
				String temp1 = cur_file_name_list.get(j);
				total_file_name_list.add(temp1);
			}
		}
		List image_byte = new ArrayList <>();
		for(int j=0; j<total_file_name_list.size(); j++) {
			byte[] image_to_byte = null;
			try {
				image_to_byte = getImgByteArray(total_file_name_list.get(j).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			image_byte.add(image_to_byte);
		}
		Message message = new Message();
		message.setData(image_byte);
		return message;	
	}
	
	@GetMapping("searchRecord")
	public Message searchRecordByKeyword(@RequestParam int user_seq, @RequestParam String keyword) {
		System.out.println("********************searchRecord********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("user_seq: "+user_seq);
		System.out.println("keyword: "+keyword);
		HashMap <String, Object> info = new HashMap<>();
		info.put("user_seq", user_seq);
		info.put("keyword", keyword);
		List <AlcholRecordResponse1> data = recordService.searchRecordByKeyword(info);
		Message message = new Message();
		message.setData(data);
		return message;
	}
	
	@GetMapping("getAllRecords")
	public Message getAllRecords() {
		System.out.println("********************getAllRecords********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		List <AlcholRecordResponse2> record_list = new ArrayList <>();
		record_list = recordService.getAllRecords();
		for(int i=0; i<record_list.size(); i++) {
			if(record_list.get(i).getImg_seq()==null) continue;
			String file_name_string = record_list.get(i).getImg_seq();
			List file_name_list = fileNameStringToList(file_name_string);
			List image_byte = new ArrayList <>();
			for(int j=0; j<file_name_list.size(); j++) {
				byte[] image_to_byte = null;
				try {
					image_to_byte = getImgByteArray(file_name_list.get(j).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				image_byte.add(image_to_byte);
			}
			record_list.get(i).setImage_byte(image_byte);
		}
		Message message = new Message();
		message.setData(record_list);
		return message;
	}

	public void deleteImg(List file_name_list) {
		for(int i = 0; i < file_name_list.size(); i++) {
			new File(image_folder_path + file_name_list.get(i)+".jpg").delete();
		}
    }
	
	public List fileNameStringToList(String file_name_string) {
		String[] file_name_arraylist = file_name_string.split(",");
		List file_name_list = Arrays.asList(file_name_arraylist);
		return file_name_list;
	}
	
	public byte[] getImgByteArray(String file_name) throws IOException{
		File image = new File(image_folder_path + file_name+".jpg");
		byte[] image_to_byte = Files.readAllBytes(image.toPath());
		return image_to_byte;
	}
	
	public AlcholRecord setAlcholRecord(AlcholRecordResponse2 foundRecord2, String file_name_string) {
		AlcholRecord record = new AlcholRecord();
		record.setAlchol_seq(foundRecord2.getAlchol_seq());
		record.setUser_seq(foundRecord2.getUser_seq());
		record.setImg_seq(file_name_string);
		if(foundRecord2.getStar()!=null) {
			record.setStar(foundRecord2.getStar());
		}
		if(foundRecord2.getAbv()!=null) {
			record.setAbv(foundRecord2.getAbv());
		}
		if(foundRecord2.getStar()!=null) {
			record.setTaste(foundRecord2.getTaste());
		}
		if(foundRecord2.getIncense()!=null) {
			record.setIncense(foundRecord2.getIncense());
		}
		if(foundRecord2.getTaste()!=null) {
			record.setTaste(foundRecord2.getTaste());
		}
		if(foundRecord2.getTexture()!=null) {
			record.setTexture(foundRecord2.getTexture());
		}
		if(foundRecord2.getImg_seq()!=null) {
			record.setImg_seq(foundRecord2.getImg_seq());
		}
		if(foundRecord2.getTime()!=null) {
			record.setTime(foundRecord2.getTime());
		}
		if(foundRecord2.getFlower()!=null) {
			record.setFlower(foundRecord2.getFlower());
		}
		if(foundRecord2.getFruit()!=null) {
			record.setFruit(foundRecord2.getFruit());
		}
		if(foundRecord2.getGrain()!=null) {
			record.setGrain(foundRecord2.getGrain());
		}
		if(foundRecord2.getNut()!=null) {
			record.setNut(foundRecord2.getNut());
		}
		if(foundRecord2.getSweetness()!=null) {
			record.setSweetness(foundRecord2.getSweetness());
		}
		if(foundRecord2.getDairy()!=null) {
			record.setDairy(foundRecord2.getDairy());
		}
		if(foundRecord2.getEtc()!=null) {
			record.setEtc(foundRecord2.getEtc());
		}
		record.setImg_seq(file_name_string);
		return record;
	}
}
