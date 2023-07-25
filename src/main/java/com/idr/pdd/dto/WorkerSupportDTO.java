package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkerSupportDTO {

	private int dataseq;
	private int workdailySeq;
	private String personid;
	private String supporttimeFrom;
	private String supporttimeTo;
	private int manhour;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String tid;
}
