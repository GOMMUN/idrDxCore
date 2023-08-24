package com.idr.pdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnomalydetectConfirmDTO {

	private int dataseq;
	private String factoryid;
	private String confirmid;
	private String confirmDatetime;
	private String confirmUpdator;
	private String confirmTarget;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String confirmReason;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
}
