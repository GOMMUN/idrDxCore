package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkContentsDTO {

	private int dataseq;
	private int workdailySeq;
	private String worktimeFrom;
	private String worktimeTo;
	private int manhour;
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
