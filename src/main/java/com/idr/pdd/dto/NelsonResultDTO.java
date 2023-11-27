package com.idr.pdd.dto;

import java.util.List;
import java.util.Map;

import com.idr.pdd.common.NelsonRulesProcessor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NelsonResultDTO {

	private double prodUtc;
	private double prodLtc;
	private double failUtc;
	private double failLtc;
	
	List<Map<String, String>> dateList;
	List<Double> prodAvgList;
	List<Double> failAvgList;
	
	private String prodResult;
	private String failResult;
	
}
