package com.idr.pdd.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotId {

	UNDER_PRODUCTION_MAIN("V9TL2LETSQ5KKUO0"),
	UNDER_PRODUCTION_VENDOR("IDA5244V2QB83AGW"),
	
	NOTOPERATE_PRESS_MAIN("AJLVQ0PR9OPA42VD"),
	NOTOPERATE_PRESS_VENDOR("74SQGQDIH3TNFU5I"),
	
	DEFECT_RATE_MAIN("PC3XTCE4B982NVGC"),
	DEFECT_RATE_VENDOR("330JNI2L1VQUKOWL")
	
	;
	
	private String bot;
}
