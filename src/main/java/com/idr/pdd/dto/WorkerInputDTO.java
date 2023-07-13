package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkerInputDTO {
	
	private int dataseq;
	private int workdailySeq;
	private String factoryid;
	private String lineid;
	private String shiftid;
	private String workDate;
	private String personid;
	private String overtime;
	private String notes;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String tid;
}
