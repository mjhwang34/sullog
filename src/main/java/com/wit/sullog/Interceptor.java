package com.wit.sullog;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class Interceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");
		String token = request.getHeader("token");
		String type = request.getHeader("type");
		if(token==null || type==null) {
			response.setStatus(401);
			return false;
		}
		if(type.equals("naver")) {
			String host = "https://openapi.naver.com//v1/nid/me";
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            
            if(responseCode==200) {
            	response.setStatus(200);
                return true;
            }
		}
		else if(type.equals("kakao")) {
			String host = "https://kapi.kakao.com/v2/user/me";
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            
            if(responseCode==200) {
            	response.setStatus(200);
                return true;
            }
		}
		response.setStatus(401);
		return false;  
	}

}
