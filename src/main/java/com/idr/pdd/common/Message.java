package com.idr.pdd.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Message {

	@Schema(description = "코드", defaultValue = "400", example = "200", allowableValues = {"200", "400"})
	private int status;
	
	@Schema(description = "내용", defaultValue = "BAD_REQUEST", example = "OK", allowableValues = {"OK", "BAD_REQUEST"})
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST.getCode();
        this.data = null;
        this.message = StatusEnum.BAD_REQUEST.getName();
    }
}
