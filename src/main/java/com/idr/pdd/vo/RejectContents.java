package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RejectContents {

	@Schema(name = "date", description = "날짜", example = "20231118", format = "yyyymmdd",  required = true)
	private String date;
	
	@Schema(name = "plant", description = "공장", example = "KEM", required = true)
	private String plant;
	
	@Schema(name = "line", description = "라인", example = "KEM-P0002", required = true)
	private String line;
	
	@Schema(name = "shift", description = "작업구분", example = "KEM-AM", required = true)
	private String shift;
	
	@Schema(name = "operation", description = "", example = "리크&디포")
	private String operation;
	
	@Schema(name = "rejectcode", description = "", example = "WARTER")
	private String rejectcode;
	
	@Schema(name = "model", description = "", example = "M00008")
	private String model;
	
	@Schema(name = "firstrejectqty", description = "", example = "13")
	private int firstrejectqty;
	
	@Schema(name = "reworkrejectqty", description = "", example = "1")
	private int reworkrejectqty;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;

}
