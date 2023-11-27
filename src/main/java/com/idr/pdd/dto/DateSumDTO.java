package com.idr.pdd.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DateSumDTO {

	private String date;
	private String year;
	private String month;
	private String day;
	private double sum;
}
