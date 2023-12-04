package com.idr.pdd.dto;

import java.util.List;
import java.util.Map;

import com.idr.pdd.common.NelsonRulesProcessor;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NelsonResultDTO {

	private double prodUcl;
	private double prodLcl;
	private double prodAvg;
	private double failUcl;
	private double failLcl;
	private double failAvg;
	
	List<Map<String, String>> dateList;
	List<Double> prodAvgList;
	List<Double> failAvgList;
	
	private String prodResult;
	private String failResult;
	
}
