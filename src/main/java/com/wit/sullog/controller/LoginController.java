package com.wit.sullog.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wit.sullog.model.Message;
import com.wit.sullog.model.NaverLogin;
import com.wit.sullog.model.NaverLoginProfile;
import com.wit.sullog.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
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
			return "redirect:http://localhost:3000?result=fail";
		}
		HashMap <String, String> loginInfo = new HashMap<>();
		loginInfo.put("type", "naver");
		loginInfo.put("email", naverLoginProfile.getEmail());
		loginInfo.put("access_token", naverLogin.getAccess_token());
		int affectedrows = loginService.insertUser(loginInfo);
		message.setData(loginInfo);
		//return message;
		//return "redirect:http://localhost:8080/hi/here";
		return "redirect:" + "http://localhost:3000?result=success&"+"type=naver&email="+loginInfo.get("email")+"&access_token="+loginInfo.get("access_token");
	}
	
	@GetMapping("/kakaoLoginCallback")
	public String kakaoLoginCallback(@RequestParam(value = "code", required = false) String code) throws Exception {
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
		//return "redirect:http://localhost:8080/hi/here";
		return "redirect:http://localhost:3000?result=success&type=kakao&email="+loginInfo.get("email")+"&accesss_token="+access_token;
	}

}
