package com.idr.pdd.common;

public enum StatusEnum {

	OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private int statusCode;
    private String code;
    
    private int statusCode() {
    	return this.statusCode;
    }

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
