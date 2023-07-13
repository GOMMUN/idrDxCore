package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkContentsDTO {

	private int dataseq;
	private int workdailySeq;
	private String factoryid;
	private String lineid;
	private String shiftid;
	private String workDate;
	private String worktimeFrom;
	private String worktimeTo;
	private String modelid;
	private String operationid;
	private int manhour;
	private int planQty;
	private int prodQty;
	private int goodsumQty;
	private int firsttimeGoodQty;
	private int firsttimeFailQty;
	private int reworkGoodQty;
	private int reworkFailQty;
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
