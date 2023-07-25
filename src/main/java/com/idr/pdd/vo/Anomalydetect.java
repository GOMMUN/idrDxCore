package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Anomalydetect {
	
	@Schema(name = "factory", description = "factory", example = "KEM", required = true)
	private String factory;
	
	@Schema(name = "messengerid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String messengerid;
	
	@Schema(name = "messengerState", description = "messengerState", example = "OCCUR", required = true)
	private String messengerState;
	
	@Schema(name = "messengerReason", description = "messengerReason", example = "UNDER-PRODUCTION")
	private String messengerReason;
	
	@Schema(name = "messengerReasondescription", description = "messengerReasondescription", example = "생산계획 대비 생산량 부족")
	private String messengerReasondescription;
	

}
