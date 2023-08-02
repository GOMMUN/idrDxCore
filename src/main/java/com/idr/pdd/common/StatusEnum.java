package com.idr.pdd.common;

import lombok.Getter;

@Getter
public enum StatusEnum {

//	OK(200, "OK"),
//    BAD_REQUEST(400, "BAD_REQUEST"),
//    NOT_FOUND(404, "NOT_FOUND"),
//    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");
	
	OK("OK",200),
    BAD_REQUEST("BAD_REQUEST",400),
    NOT_FOUND("NOT_FOUND",404),
    INTERNAL_SERER_ERROR("INTERNAL_SERVER_ERROR",500);

    private int code;
    private String name;
    
    StatusEnum( String name, int code) {
        this.code = code;
        this.name = name;
    }
}
