package com.idr.pdd.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.google.gson.JsonObject;
import com.idr.pdd.exception.MessageSendException;

public class SendBlockit {

	@SuppressWarnings("deprecation")
	public static String BlockitToken(String botId) throws Exception{
		
		URL url = null;
        HttpsURLConnection conn = null;
        String token = null;
        
        try {
        	url = new URL("https://chat.teamply.co.kr/api/v1/blockkit/token");
        	ignoreSsl();
        	
        	conn = (HttpsURLConnection)url.openConnection();  						// Connection 연결
        	conn.setDoInput(true);
 			conn.setRequestProperty("bot-id", botId);
 			conn.setRequestMethod("GET");
 			conn.setDoOutput(true);
 			
 			BufferedReader br = null;
 			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  // BufferedReader객체 생성
 			
 			String input = null;
 			
 			String ret = new String();
 			
 			//데이터를 읽어드림
            while ((input = br.readLine()) != null){
                ret += input;
            }
            
            JsonObject jo = JsonUtils.getJsonObject(ret);
            
            if(jo.get("result").getAsBoolean()) {
            	token = jo.get("data").getAsJsonObject().get("botToken").getAsString();
            }else {
            	throw new MessageSendException();
            }
            
            br.close();
        }catch (Exception e) {
        	throw new MessageSendException();
		}finally {
            if (conn != null) {
            	conn.disconnect();    												// Connection 연결 끊기
            }
        }
        
        return token;
	}
	
	@SuppressWarnings("deprecation")
	public static String BlockitMesaageSend(String botId, String botToken, String message) throws Exception{
		
		URL url = null;
        HttpsURLConnection conn = null;
        String ret = new String();
        
        try {
        	url = new URL("https://chat.teamply.co.kr/api/v1/blockkit/send");
        	ignoreSsl();
        	
        	conn = (HttpsURLConnection)url.openConnection();  						// Connection 연결
        	conn.setDoInput(true);
        	conn.setRequestProperty("bot-token", botToken);
 			conn.setRequestProperty("bot-id", botId);
 			conn.setRequestMethod("POST");
 			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 			conn.setDoOutput(true);
 			
 			String param = "message=";
 			
 			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			osw.write(param+URLEncoder.encode(message, "UTF-8"));
			osw.flush();
			osw.close();
 			
 			BufferedReader br = null;
 			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  // BufferedReader객체 생성
 			
 			String input = null;
 			
 			 //데이터를 읽어드림
            while ((input = br.readLine()) != null){
                ret += input;
            }
            
            br.close();
        }catch (Exception e) {
        	throw new MessageSendException();
		}finally {
            if (conn != null) {
            	conn.disconnect();    												// Connection 연결 끊기
            }
        }
        
        return ret;
	}
	
	private static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
	
	private static void trustAllHttpsCertificates() throws Exception {
	    TrustManager[] trustAllCerts = new TrustManager[1];
	    TrustManager tm = new miTM();
	    trustAllCerts[0] = tm;
	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, trustAllCerts, null);
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
}
