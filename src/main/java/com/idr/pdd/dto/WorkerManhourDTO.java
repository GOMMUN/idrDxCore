package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkerManhourDTO {

	private int dataseq;
	private int workdailySeq;
	private String factoryid;
	private String lineid;
	private String shiftid;
	private String workDate;
	private String inputItemid;
	private int hands;
	private int manhour;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String tid;
}
