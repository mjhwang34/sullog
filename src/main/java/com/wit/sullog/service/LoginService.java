package com.wit.sullog.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.wit.sullog.mapper.MainMapper;
import com.wit.sullog.model.NaverLogin;
import com.wit.sullog.model.NaverLoginProfile;
import com.wit.sullog.model.NaverLoginProfileResponse;
import com.wit.sullog.model.NaverLogout;
import com.wit.sullog.model.User;

@Service
public class LoginService {
	@Autowired
    private WebClient webClient;

    @Value("D_1pjm3y2CmU8A7NfcnD")
    private String client_id;

    @Value("hnxqkGWAqP")
    private String client_secret;
    
    private final MainMapper mainMapper;
    
    @Autowired
	public LoginService(MainMapper mainMapper) {this.mainMapper = mainMapper; }

    /**
     * @description Naver �α����� ���Ͽ� Access_tokin�� �߱޹���
     * @param resValue
     *          1) code: ��ū �߱޿� 1ȸ�� �ڵ�
     *          2) state: CORS �� �����ϱ� ���� ������ ��ū
     * @param grant_type
     *          1) �߱�:'authorization_code'
     *          2) ����:'refresh_token'
     *          3) ����: 'delete'
     * @return
     */
    
    public NaverLogin requestNaverLoginAccessToken(Map<String, String> resValue, String grant_type){
        final String tokenUri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", grant_type)
                .queryParam("client_id", this.client_id)
                .queryParam("client_secret", this.client_secret)
                .queryParam("code", resValue.get("code"))
                .queryParam("state", resValue.get("state"))
                .build()
                .encode()
                .toUriString();
        
        return webClient
                .get()
                .uri(tokenUri)
                .retrieve()
                .bodyToMono(NaverLogin.class)
                .block();
    }

 // ----- ������ API ȣ�� (Unique�� id ���� �������� ����) -----
    public NaverLoginProfile requestNaverLoginProfile(NaverLogin naverLogin){
        final String profileUri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .build()
                .encode()
                .toUriString();

        return webClient
                .get()
                .uri(profileUri)
                .header("Authorization", "Bearer " + naverLogin.getAccess_token())
                .retrieve()
                .bodyToMono(NaverLoginProfileResponse.class)
                .block()
                .getResponse();
    }
    
    public NaverLogout requestNaverLogout(String token) {
    	final String logoutUri = UriComponentsBuilder
    			.fromUriString("https://nid.naver.com")
    			.path("/oauth2.0/token")
    			.queryParam("grant_type", "delete")
                .queryParam("client_id", this.client_id)
                .queryParam("client_secret", this.client_secret)
                .queryParam("access_token", token)
                .queryParam("service_provider", "NAVER")
                .build()
                .encode()
                .toUriString();
    	return webClient
    			.get()
    			.uri(logoutUri)
    			.retrieve()
    			.bodyToMono(NaverLogout.class)
                .block();
    }
    
    public String requestKakaoLoginAccessToken (String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST ��û�� ���� �⺻���� false�� setDoOutput�� true��
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST ��û�� �ʿ�� �䱸�ϴ� �Ķ���� ��Ʈ���� ���� ����
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            
			sb.append("&client_id=c12449cd7cb9fadf119897f40e9067f3"); //������ �߱޹��� rest api key
			sb.append("&redirect_uri=http://52.78.33.186:8080/sullog/login/kakaoLoginCallback"); // ������ ������ �ּ�
			//sb.append("&redirect_uri=http://localhost:8080/login/kakaoLoginCallback"); // ������ ������ �ּ�
            
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
            
			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
            
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);
           
			// Gson ���̺귯���� ���Ե� Ŭ������ JSON�Ľ� ��ü ����
			JSONParser parser = new JSONParser();
			JSONObject element = (JSONObject) parser.parse(result);
            
			access_Token = element.get("access_token").toString();
			refresh_Token = element.get("refresh_token").toString();
            
			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
            e.printStackTrace();
        }
		return access_Token;
	}
    
    public HashMap<String, String> requestKakaoLoginProfile(String access_token) throws IOException {
        String host = "https://kapi.kakao.com/v2/user/me";
        HashMap<String, String> result = new HashMap<>();
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while((line=br.readLine())!=null)
            {
                res+=line;
            }

            System.out.println("res = " + res);


            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            //JSONObject properties = (JSONObject) obj.get("properties");

            String email = kakao_account.get("email").toString();
            
            result.put("email", email);

            br.close();


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public HashMap<String, String> requestKakaoLogout(String access_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        HashMap<String, String> result = new HashMap<>();
        String res = "";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";

            while ((line = br.readLine()) != null) {
                res += line;
            }
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            
            result.put("id", obj.get("id").toString());
            
            
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
        	res="error";
        	result.put("error", "error");
            e.printStackTrace();
        }
        return result;
    }
    
    public int insertUser(HashMap <String, String> loginInfo) {
    	return mainMapper.insertUser(loginInfo);
    }
    
    public User getUserInfoByEmail(HashMap<String, String> info) {
    	return mainMapper.getUserInfoByEmail(info);
    }
    
    public boolean validUser(HashMap<String, String> info) {
    	String type = info.get("type");
    	String host = "";
    	if(type.equals("naver")) {
    		host = "https://openapi.naver.com//v1/nid/me";
    	}
    	else if(type.equals("kakao")){
    		host = "https://kapi.kakao.com/v2/user/me";
    	}
		try {
	        URL url;
			url = new URL(host);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setRequestProperty("Authorization", "Bearer " + info.get("token"));
	        urlConnection.setRequestMethod("GET");
	        int responseCode = urlConnection.getResponseCode();
	        System.out.println("responseCode = " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while((line=br.readLine())!=null)
            {
                res+=line;
            }

            System.out.println("res = " + res);


            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            if(type.equals("naver")) {
            	obj = (JSONObject) obj.get("response");
            }
            else if(type.equals("kakao")) {
            	obj = (JSONObject) obj.get("kakao_account");
            }

            String email = obj.get("email").toString();
            String curEmail = info.get("email");
            
            System.out.println(email);
            
            br.close();
            if(email.equals(curEmail)) return true;
            else return false;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
}
