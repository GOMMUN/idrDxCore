package com.idr.pdd.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotId {

	UNDER_PRODUCTION_MAIN("HITKSZRCVCL7C3HM"),
	UNDER_PRODUCTION_VENDOR("38MZKGG4HD2NTO4S"),
	
	NOTOPERATE_PRESS_MAIN("L2E6IAFW9KAZ4ZHT"),
	NOTOPERATE_PRESS_VENDOR("82ZAVEDFV7WEUSBS"),
	
	DEFECT_RATE_MAIN("2GTR2DMTHDXNPCKA"),
	DEFECT_RATE_VENDOR("MA91V6AA52TSUB52")
	
	;
	
	private String bot;
}
