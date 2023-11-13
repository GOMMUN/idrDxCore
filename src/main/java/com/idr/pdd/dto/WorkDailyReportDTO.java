package com.idr.pdd.dto;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class WorkDailyReportDTO {

	private int dataseq;
	private String factoryid;
	private String workDate;
	private String lineid;
	private String shiftid;
	private String modelid;
	private String materialid;
	private String register;
	private int planQty;
	private String notes;
	private String creator;
	private String createtime;
	private String event;
	private String eventuser;
	private String eventtime;
	private String isusable;
	private String tid;
	
	@Override
	public int hashCode() {
		return Objects.hash(dataseq);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkDailyReportDTO other = (WorkDailyReportDTO) obj;
		return dataseq == other.dataseq;
	}
	
	
}
