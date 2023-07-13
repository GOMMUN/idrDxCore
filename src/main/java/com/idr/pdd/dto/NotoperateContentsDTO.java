package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotoperateContentsDTO {

	private int dataseq;
	private int workdailySeq;
	private String factoryid;
	private String lineid;
	private String shiftid;
	private String workDate;
	private String notoperatetimeFrom;
	private String notoperatetimeTo;
	private int hands;
	private int manhour;
	private String cause;
	private String correctiveAction;
	private String notes;
	private String images;
	private String movies;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String tid;
}