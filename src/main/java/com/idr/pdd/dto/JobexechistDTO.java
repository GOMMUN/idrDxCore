package com.idr.pdd.dto;

import lombok.Data;

@Data
public class JobexechistDTO {

	private String execTid;
	private String execMasterid;
	private String state;
	private String execStarttime;
	private String execEndtime;
	private String messageError;
	private String lasteventtime;
}
