package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.mapper.AlarmSettingMapper;
import com.idr.pdd.mapper.BlockKitMapper;

@Service
public class AlarmService {
	
	private static final String UNDER_PRODUCTION = "UNDER-PRODUCTION";				// 계획대비 생산량 부족 (납기지연)
	private static final String NOTOPERATE_PRESS = "NOTOPERATE-PRESS";				// 프레스 설비 작동 이상 (설비이상)
	private static final String FIRST_ARTICLE_CAUTION = "FIRST-ARTICLE-CAUTION";	// 초도품 확인-이상사전감지 (품질/물류이상)
	
	@Autowired
	AlarmSettingMapper alarmSettingMapper;
	
	@Autowired
	BlockKitMapper blockKitMapper;

	public void underProduction(String plant, int planQty, int prodQty) throws Exception{
		
		// 백분율
		int percent = (int)((double) prodQty / (double) planQty * 100);
		
		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);
		
		// 설정값보다 백분율이 작을경우 알람 발생
		if(value > percent) {
			
			if("KEM".equals(plant)) {
				// 통보 > 확인 ( 대표기업 )
			}else {
				// 발생 > 통보 > 확인 ( 협럽사 )
			}
			
			
			
			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
		}
	}
	
	
	
}
