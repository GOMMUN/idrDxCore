package com.idr.pdd.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Occur {

	private String factory;
	private String messengerid;
	private String messengerState;
	private String messengerDatetime;
	private String messengerReason;
	private String messengerReasondescription;
	
	@Schema(name = "tid", description = "tid", example = "7WOR5B3B-242B-4696-AD74-F9D6D8TE0002", required = true)
	private String tid;

}
