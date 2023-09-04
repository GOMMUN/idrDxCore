package com.idr.pdd.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BlockKitDataParshing {

	public static String setUnderProductionOccurUrl(String plant, String material, String tid, int planQty, int prodQty,
			int percent) {

		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/underProduction/notice" + "?plant=" + plant + "&tid="
				+ tid + "&material=" + material + "&planQty=" + planQty + "&prodQty=" + prodQty + "&percent=" + percent;

		return url;
	}

	public static String setUnderProductionNoticeUrl(String plant, String material, String tid, int planQty,
			int prodQty, int percent) {

		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/underProduction/confirm" + "?plant=" + plant + "&tid="
				+ tid;

		return url;
	}

	public static String setDefectRateOccurUrl(String plant, String material, String tid, String prodDate, int failQty,
			int prodQty, int percent) {

		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/defectRate/notice" + "?plant=" + plant + "&material="
				+ material + "&tid=" + tid + "&prodDate=" + prodDate + "&failQty=" + failQty + "&prodQty=" + prodQty
				+ "&percent=" + percent;

		return url;
	}

	public static String setDefectRateNoticeUrl(String plant, String material, String tid, String prodDate, int failQty,
			int prodQty, int percent) {

		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/defectRate/confirm" + "?plant=" + plant + "&tid="
				+ tid;

		return url;
	}
	
	public static String setNotOperatepressOccurUrl(String plant, String line,String getdate, String tid) {
		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/notOperatepress/notice" + "?plant=" + plant +"&line="+line
				+"&getdate="+getdate+"&tid="+ tid ;

		return url;
	}
	
	public static String setNotOperatepressNoticeUrl(String plant, String line,String getdate, String tid) {
		String req = null;

		String url = "https://idrenvision.iptime.org:8171/message/notOperatepress/confirm" + "?plant=" + plant +"&line="+line
				+"&getdate="+getdate+"&tid="+ tid ;

		return url;
	}

	// 계획대비 생산량 부족
	public static String underProduction(String blockKit, String btnString, String btnUrl, String plantName,
			String materialName, int planQty, int prodQty, int percent) {

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(blockKit);

		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();

		int index = 0;
		for (JsonElement jsonElement : jsonArray) {

			if (index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", plantName);
			} else if (index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", materialName);
			} else if (index == 4) {
				// 생산/계획
				String str = prodQty + "/" + planQty + ", " + percent + "%";
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", str);
			} else if (index == 6) {
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

	public static String notoperatePress(String blockKit, String btnString, String btnUrl, String plantName,
			String lineName, String getdate) {

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(blockKit);

		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();

		int index = 0;
		for (JsonElement jsonElement : jsonArray) {

			if (index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", plantName);
			} else if (index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", lineName);
			} else if (index == 4) {
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", getdate);
			} else if (index == 6) {
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

	public static String defectRate(String blockKit, String btnString, String btnUrl, String prodDate,
			String materialName, int failQty, int prodQty, int percent) {
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(blockKit);

		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();

		int index = 0;
		for (JsonElement jsonElement : jsonArray) {

			if (index == 2) {
				// 생산일자
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", prodDate);
			} else if (index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", materialName);
			} else if (index == 4) {
				// 불량률
				String str = failQty + "/" + prodQty + ", " + percent + "%";
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", str);
			} else if (index == 6) {
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

	public static String firstArticleCaution(String blockKit, String btnString, String btnUrl, String plantName,
			String lineName) {

		JsonObject jsonObject = (JsonObject) new JsonParser().parse(blockKit);

		JsonArray jsonArray = jsonObject.get("blocks").getAsJsonArray();

		int index = 0;
		for (JsonElement jsonElement : jsonArray) {

			if (index == 2) {
				// 회사명(공장명)
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", plantName);
			} else if (index == 3) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", lineName);
			} else if (index == 4) {
				// 자재명
				jsonElement.getAsJsonObject().get("inlines").getAsJsonArray().get(1).getAsJsonObject()
						.addProperty("text", lineName);
			} else if (index == 6) {
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
