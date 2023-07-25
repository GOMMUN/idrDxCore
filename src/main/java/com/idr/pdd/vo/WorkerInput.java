package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class WorkerInput {
	
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

	@Schema(name = "worker", description = "성명", example = "KEM-W0001", required = true)
	private String worker;
	
	@Schema(name = "overtime", description = "잔업", example = "Y")
	private String overtime;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
}
