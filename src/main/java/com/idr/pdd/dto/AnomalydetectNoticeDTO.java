package com.idr.pdd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnomalydetectNoticeDTO {

	private int dataseq;
	private String factoryid;
	private String noticeid;
	private String noticeDatetime;
	private String noticeUpdator;
	private String noticeTarget;
	private String noticeReason;
	private String noticeReasondescRiption;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;
}
