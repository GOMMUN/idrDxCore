package com.idr.pdd.common;

import lombok.Data;

@Data
public class Message {

	private int status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST.getCode();
        this.data = null;
        this.message = StatusEnum.BAD_REQUEST.getName();
    }
}
