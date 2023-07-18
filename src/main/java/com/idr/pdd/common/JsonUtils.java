package com.idr.pdd.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * JSON 관련된 유틸리티 클래스
 */
public class JsonUtils {


	/**
	 * JSONString을 List<Map>으로 변환
	 *
	 * @param JSONString
	 * @return List<Map>
	 */
	public static List<Map<String, Object>> getListMap(String jsonString){
		return getListMapFromJsonObject(getJsonObject(jsonString));
	}

	/**
	 * JSONString을 JsonObject로 변환
	 *
	 * @param JSONString
	 * @return JsonObject
	 */
	public static JsonObject getJsonObject(String jsonString) {
		/*
		 * payload Format
		 * { username:aaa, address:bbb, bunji : 1}
		 * {table:[{ username:aaa, address:bbb, bunji : 1},{ username:bbb, address:ccc, bunji : 3}]}
		 *
		 * {"table":[{ "username":"aaa", "address":"bbb", "bunji" : "1"},{ "username":"bbb", "address":"ccc", "bunji" : "2"}]}
		 */
		JsonParser jsonParser = new JsonParser();

		JsonObject jsonObj = jsonParser.parse(jsonString).getAsJsonObject();

		return jsonObj;
	}

	public static JsonObject getJsonObject(String jsonString, String key) {

		JsonParser jsonParser = new JsonParser();

		JsonObject jsonObj = jsonParser.parse(jsonString).getAsJsonObject();
		return jsonObj.get(key).getAsJsonObject();
	}

	public static JsonObject getJsonObject(JsonObject jsonObj, String key) {
		return jsonObj.get(key).getAsJsonObject();
	}

	public static JsonArray getJsonArray(JsonObject jsonObj, String key) {
		return jsonObj.get(key).getAsJsonArray();
	}

	public static JsonArray getJsonArray(JsonObject jsonObj) {
		return jsonObj.getAsJsonArray();
	}

	/**
	 * JsonObject을 Map으로 변환
	 *
	 * @param JsonObject
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonObject(JsonObject jsonObj){
	    Map<String, Object> map = null;

	    try {
	       map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return map;
	}

	/**
	 * JSONString을 Map으로 변환
	 *
	 * @param JSONString
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonString(String jsonString){
	    Map<String, Object> map = null;

	    try {
	       map = new ObjectMapper().readValue(jsonString, Map.class);
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return map;
	}

	/**
	 * JsonObject을 List<Map>으로 변환
	 *
	 * @param JsonObject
	 * @return List<Map>
	 */
	public static List<Map<String, Object>> getListMapFromJsonObject(JsonObject jsonObj){
		List<Map<String, Object>> list = null;

	    try {
	    	list = new ObjectMapper().readValue(jsonObj.get("table").toString(), new TypeReference<List<Map<String, Object>>>() {});
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return list;
	}

	/**
	 * JSONString을 List<Map>으로 변환
	 *
	 * @param JSONString
	 * @return List<Map>
	 */
	public static List<Map<String, Object>> getListMapFromJsonString(String jsonString){
		List<Map<String, Object>> list = null;

	    try {
	    	list = new ObjectMapper().readValue(jsonString.toString(), new TypeReference<List<Map<String, Object>>>() {});
	    } catch (JsonParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return list;
	}
}
