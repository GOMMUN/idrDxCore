package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NelsonruleSettingDTO {

	private int dataseq;
	private int length;
	private int limit;
	private int nelsonRule;
	private String ischecked;
	private String alarmType;
	private String typeName;
}
