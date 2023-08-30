package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WorkContents {
	
	@Schema(name = "date", description = "날짜", example = "20231118", format = "yyyymmdd",  required = true)
	private String date;
	
	@Schema(name = "plant", description = "공장", example = "KEM", required = true)
	private String plant;
	
	@Schema(name = "line", description = "라인", example = "KEM-P0002", required = true)
	private String line;
	
	@Schema(name = "shift", description = "작업구분", example = "KEM-AM", required = true)
	private String shift;
	
	@Schema(name = "model", description = "모델", example = "MODEL1")
	private String model;
	
	@Schema(name = "material", description = "자재", example = "MOT000324/C")
	private String material;

	@Schema(name = "fromtime", description = "시작시간", example = "080000", required = true)
	private String fromtime;
	
	@Schema(name = "totime", description = "종료시간", example = "095500", required = true)
	private String totime;
	
	@Schema(name = "manhour", description = "사용공수", example = "")
	private int manhour;
	
	@Schema(name = "prodqty", description = "생산수량", example = "")
	private int prodqty;
	
	@Schema(name = "firstgoodqty", description = "직행양품수량", example = "")
	private int firstgoodqty;
	
	@Schema(name = "firstfailqty", description = "직행불량수량", example = "")
	private int firstfailqty;
	
	@Schema(name = "reworkgoodqty", description = "재작업양품수량", example = "")
	private int reworkgoodqty;
	
	@Schema(name = "reworkfailqty", description = "재작업불량수량", example = "")
	private int reworkfailqty;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
	
	@Schema(name = "notes", description = "notes", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String notes;
	
}
