package com.idr.pdd.common;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BlockKitDataParshing {
	
	private static String setUrl(String btnUrl, String plantName, String materialName, int planQty, int prodQty, int percent) {
		
		String req = null;
		
		String url = "https://idrenvision.iptime.org:8071/message/underProduction"
				+ "?plantName="+plantName
				+ "&materialName="+materialName
				+ "&planQty="+planQty
				+ "&prodQty="+prodQty
				+ "&percent="+percent
				+ "&btnUrl="+btnUrl;
		
		return url;
	}
	
	// 계획대비 생산량 부족
	public static String underProduction(String blockKit, String btnString, String btnUrl, String plantName, String materialName, int planQty, int prodQty, int percent) {
		
		JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
	
		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
		
		int index = 0;
		for (JsonElement jsonElement : jsonArray) {
			
			if(index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", plantName);
			}else if(index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", materialName);
			}else if(index == 4) {
				// 생산/계획 
				String str = prodQty + "/" + planQty + ", " + percent + "%";
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", str);
			}else if(index == 6) {
				// 버튼 text
				jsonElement.getAsJsonObject().addProperty("text", btnString);

				// 버튼 URL
				jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", btnUrl);
			}
			
			
			index++;
		}
		blockKit = jsonObject.toString();
		
		return blockKit;
	}

	public static String notoperatePress(String blockKit, String btnString, String btnUrl, String plantName, String lineName) {
		
		JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
	
		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
		
		int index = 0;
		for (JsonElement jsonElement : jsonArray) {
			
			if(index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", plantName);
			}else if(index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", lineName);
			}else if(index == 5) {
				// 버튼 text
				jsonElement.getAsJsonObject().addProperty("text", btnString);

				// 버튼 URL
				jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", btnUrl);
			}
			
			
			index++;
		}
		blockKit = jsonObject.toString();
		
		return blockKit;
	}
	

	public static String firstArticleCaution(String blockKit, String btnString, String btnUrl, String plantName, String lineName) {
		
		JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
	
		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
		
		int index = 0;
		for (JsonElement jsonElement : jsonArray) {
			
			if(index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", plantName);
			}else if(index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", lineName);
			}else if(index == 4) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
							.getAsJsonObject().addProperty("text", lineName);
			}else if(index == 6) {
				// 버튼 text
				jsonElement.getAsJsonObject().addProperty("text", btnString);

				// 버튼 URL
				jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", btnUrl);
			}
			
			index++;
		}
		blockKit = jsonObject.toString();
		
		return blockKit;
	}
}
