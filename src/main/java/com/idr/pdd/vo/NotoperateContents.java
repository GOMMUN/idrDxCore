package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NotoperateContents {
	
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
	
	@Schema(name = "fromtime", description = "시작시간", example = "124000")
	private String fromtime;
	
	@Schema(name = "totime", description = "종료시간", example = "133000")
	private String totime;
	
	@Schema(name = "man", description = "", example = "1")
	private int man;
	
	@Schema(name = "manhour", description = "", example = "50")
	private int manhour;
	
	@Schema(name = "contentcause", description = "", example = "3점 5냉")
	private String contentcause;
	
	@Schema(name = "correctiveaction", description = "", example = "카파1.4 기종교체")
	private String correctiveaction;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
}
