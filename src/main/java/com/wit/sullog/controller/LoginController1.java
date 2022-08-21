package com.wit.sullog.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wit.sullog.model.Message;
import com.wit.sullog.model.NaverLogin;
import com.wit.sullog.model.NaverLoginProfile;
import com.wit.sullog.model.NaverLogout;
import com.wit.sullog.model.User;
import com.wit.sullog.service.LoginService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/_login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController1 {

	@Autowired
	private LoginService loginService;
	
	/*
	@GetMapping("/naverLoginCallback")
	public String naverLoginCallback(@RequestParam Map <String, String> resValue) {
		System.out.println("********************naverLoginCallback********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("resValue: "+ resValue);
		Message message = new Message();
		NaverLogin naverLogin = null;
		NaverLoginProfile naverLoginProfile = null;
		try {
			// code 를 사용하여 access_token를 발급받는다.
	        naverLogin = loginService.requestNaverLoginAccessToken(resValue, "authorization_code");
	        // access_token를 사용하여 사용자의 고유 id값을 가져온다.
	        naverLoginProfile = loginService.requestNaverLoginProfile(naverLogin);
	        System.out.println("token : " + naverLogin.getAccess_token());
		}catch(Exception e) {
			e.printStackTrace();
			message.setCode("005");
			message.setResult("authorization invalid");
			//return message;
			return "redirect:" + "https://localhost:3000?result=fail";
		}
		HashMap <String, String> loginInfo = new HashMap<>();
		loginInfo.put("type", "naver");
		loginInfo.put("email", naverLoginProfile.getEmail());
		loginInfo.put("access_token", naverLogin.getAccess_token());
		loginInfo.put("name", naverLoginProfile.getName());
		loginInfo.put("gender", naverLoginProfile.getGender());
		loginInfo.put("birthday", naverLoginProfile.getBirthday());
		loginInfo.put("birthyear", naverLoginProfile.getBirthyear());
		loginInfo.put("mobile", naverLoginProfile.getMobile());
		int affectedrows = loginService.insertUser(loginInfo);
		message.setData(loginInfo);
		//return message;
		return "redirect:http://localhost:8080/hi/here";
		//return "redirect:" + "http://localhost:3000?result=success&"+"type=naver&email="+loginInfo.get("email")+"&access_token="+loginInfo.get("access_token")+"&name="+loginInfo.get("name")+"&gender="+loginInfo.get("gender")+"&birthday="+loginInfo.get("birthday")+"&birthyear="+loginInfo.get("birthyear")+"&mobile="+loginInfo.get("mobile");
	}*/
	
	@GetMapping("/isLogin")
	public Message isLogin(@RequestParam String type, @RequestParam String token) {
		System.out.println("********************isLogin********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("type: "+ type);
		System.out.println("token: "+ token);
		Message message = new Message();
		NaverLogin naverLogin = new NaverLogin();
		naverLogin.setAccess_token(token);
		NaverLoginProfile naverLoginProfile = null;
		if(type.equals("naver")) {
			try {
				naverLoginProfile = loginService.requestNaverLoginProfile(naverLogin);
				message.setData(naverLoginProfile);
			}catch(Exception e){
				e.printStackTrace();
				message.setCode("001");
				message.setResult("logout_state");
				return message;
			}
		}
		else if(type.equals("kakao")) {
			HashMap <String, String> kakaoLoginProfile = new HashMap<String, String>();
			try {
				kakaoLoginProfile = loginService.requestKakaoLoginProfile(token);
				if(kakaoLoginProfile.size() == 0) {
					System.out.println("logout");
					message.setCode("001");
					message.setResult("logout_state");
					return message;
				}
				message.setData(kakaoLoginProfile);
				return message;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return message;
	}
	
	@GetMapping("/logout")
	public Message logout(@RequestParam String type, @RequestParam String token) {
		System.out.println("********************logout********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("token: "+ type);
		System.out.println("token: "+ token);
		Message message = new Message();
		if(type.equals("naver")) {
			NaverLogout naverLogout = loginService.requestNaverLogout(token);
			message.setData(naverLogout);
		}
		else if(type.equals("kakao")) {
			HashMap<String, String> res = loginService.requestKakaoLogout(token);
			message.setData(res);
		}
		return message;
	}
	
	
	@GetMapping("/getUserInfo")
	public Message getUserInfoByEmail(@RequestParam String email, @RequestParam String type, @RequestParam String token) {
		System.out.println("********************getUserInfo********************");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(date));
		System.out.println("email: "+ email);
		HashMap<String, String> info = new HashMap<String, String>();
		Message message = new Message();
		info.put("token", token);
		info.put("email", email);
		info.put("type", type);
		boolean check = loginService.validUser(info);
		if(check==false) {
			message.setCode("005");
			message.setResult("Authorization invalid");
			return message;
		}
		User user = loginService.getUserInfoByEmail(info);
		if(user==null) {
			message.setCode("002");
			message.setResult("user doesn't exist");
			return message;
		}
		message.setData(user);
		return message;
	}
	
	/*
	@GetMapping("/kakaoLoginCallback")
	public Message kakaoLoginCallback(@RequestParam(value = "code", required = false) String code) throws Exception {
		Message message = new Message();
		System.out.println("#########" + code);
		String access_token = loginService.requestKakaoLoginAccessToken(code);
		System.out.println("###access_Token#### : " + access_token);
		HashMap <String, String> loginInfo = new HashMap<>();
		loginInfo = loginService.requestKakaoLoginProfile(access_token);
		loginInfo.put("type", "kakao");
		loginInfo.put("access_token", access_token);
		System.out.println(loginInfo);
		int affectedrows = loginService.insertUser(loginInfo);
		message.setData(loginInfo);
		return message;
	}*/
	

}
