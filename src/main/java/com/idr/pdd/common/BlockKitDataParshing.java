package com.idr.pdd.common;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BlockKitDataParshing {
	
	public String setUrl(String level) {
		
		return null;
	}

	public static String underProduction(String level, String blockKit, String plantName, int planQty, int prodQty) {
		
		if("OCCUR".equals(level)) {
			// 발생
			//JSONParser parser = new JSONParser(blockKit);
			
			JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
			
			JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
			
			System.out.println(jsonObject);
			
			int index = 0;
			for (JsonElement jsonElement : jsonArray) {
				
				if(index == 1) {
					// 공장명
					jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
								.getAsJsonObject().addProperty("text", plantName);
				}else if(index == 2) {
					// 납품물량
					jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
								.getAsJsonObject().addProperty("text", Integer.toString(planQty));
				}else if(index == 3) {
					// 생산물량
					jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1)
								.getAsJsonObject().addProperty("text", Integer.toString(prodQty));
				}else if(index == 5) {
					// 버튼 URL
					jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", "123");
				}
				
				index++;
			}
			blockKit = jsonObject.toString();
			
		}else if("NOTICE".equals(level)) {
			// 발생
			//JSONParser parser = new JSONParser(blockKit);
			
			JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
			
			JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
			
			System.out.println(jsonObject);
			
			int index = 0;
			for (JsonElement jsonElement : jsonArray) {
				
				if(index == 5) {
					// 버튼 URL
					jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", "123");
				}
				
				index++;
			}
			blockKit = jsonObject.toString();
			
		}else if("CONFIRM".equals(level)) {
			// 발생
			//JSONParser parser = new JSONParser(blockKit);
			
			JsonObject jsonObject = (JsonObject)new JsonParser().parse(blockKit);
			
			JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();
			
			System.out.println(jsonObject);
			
			int index = 0;
			for (JsonElement jsonElement : jsonArray) {
				
				if(index == 5) {
					// 버튼 URL
					jsonElement.getAsJsonObject().get("action").getAsJsonObject().addProperty("value", "123");
				}
				
				index++;
			}
			blockKit = jsonObject.toString();
		}
		
		return blockKit;
	}
}
