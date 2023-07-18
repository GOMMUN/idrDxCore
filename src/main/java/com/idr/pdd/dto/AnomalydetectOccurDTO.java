package com.idr.pdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnomalydetectOccurDTO {

	private int dataseq;
	
	@Schema(name = "factoryid", description = "공장", example = "F00001", required = true)
	private String factoryid;
	
	@Schema(name = "occurid", description = "이상감지아이디", example = "occurid", required = true)
	private String occurid;
	
	@Schema(name = "occurDatetime", description = "감지시간", example = "date", required = true)
	private String occurDatetime;
	
	@Schema(name = "occurUpdator", description = "Updator", example = "Updator", required = true)
	private String occurUpdator;
	
	@Schema(name = "occurTarget", description = "occurTarget", example = "Target", required = true)
	private String occurTarget;
	
	@Schema(name = "occurReason", description = "Updator", example = "data", required = true)
	private String occurReason;
	
	@Schema(name = "occurReasondescRiption", description = "Updator", example = "data", required = true)
	private String occurReasondescRiption;
	
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
}
