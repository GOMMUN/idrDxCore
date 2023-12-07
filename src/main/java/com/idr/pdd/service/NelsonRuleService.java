package com.idr.pdd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.common.NelsonRulesProcessor;
import com.idr.pdd.dto.NelsonruleSettingDTO;
import com.idr.pdd.dto.DateSumDTO;
import com.idr.pdd.dto.NelsonResultDTO;
import com.idr.pdd.mapper.NelsonRuleMapper;
import com.idr.pdd.mapper.NelsonRuleSettingMapper;

@Service
public class NelsonRuleService {

	@Autowired
	private NelsonRuleMapper nelsonMapper;
	
	@Autowired
	private NelsonRuleSettingMapper nelsonSettingMapper;
	
	public NelsonResultDTO find(Map<String, String> params) throws Exception {

		// 넬슨룰 설정 정보 불러오기
		List<NelsonruleSettingDTO> underProductionSetting = nelsonSettingMapper.findByAlarmType("UNDER-PRODUCTION"); // 생산량 부족
		List<NelsonruleSettingDTO> defectRateSetting = nelsonSettingMapper.findByAlarmType("DEFECT-RATE"); 			 // 불량율
		
		// 일별 총합 수량
		List<DateSumDTO> planSumList = nelsonMapper.findByPlanSum(params);	// 일별 계획수량 총합
		List<DateSumDTO> prodSumList = nelsonMapper.findByProdSum(params);	// 일별 생상수량 총합
		List<DateSumDTO> failSumList = nelsonMapper.findByFailSum(params);	// 일별 불량수량 총합
		
		List<Map<String, String>> dateList = new ArrayList<>();
		
		// 작업일보에서 조회된 생산률 리스트
		List<Double> numArray1 = new ArrayList<>(); 
		for (int i = 0; i < planSumList.size(); i++) {
			
			double planSum = planSumList.get(i).getSum();
			double goodSum = prodSumList.get(i).getSum();
			
			double prodAvg = 0.0;
			if(planSum != 0.0) {
				prodAvg = ( goodSum / planSum ) * 100;
			}
			numArray1.add(prodAvg);
			
			Map<String, String> dateMap = new HashMap<>();
			dateMap.put("year", planSumList.get(i).getYear());
			dateMap.put("month", planSumList.get(i).getMonth());
			dateMap.put("day", planSumList.get(i).getDay());
			
			dateList.add(dateMap);
		}
		
		// 작업일보에서 조회된 불량률 리스트
		List<Double> numArray2 = new ArrayList<>(); 
		for (int i = 0; i < prodSumList.size(); i++) {
			
			double prodSum = prodSumList.get(i).getSum() + failSumList.get(i).getSum();
			double failSum = failSumList.get(i).getSum();
			
			double failAvg = 0.0;
			if(prodSum != 0.0) {
				failAvg = ( failSum / prodSum ) * 100;
			}
			numArray2.add(failAvg);
		}
		
		// 생산률 각월 1일~말일 
		List<Double> ucllcl1 = NelsonRulesProcessor.getUclLcl(numArray1);
		double UCL1 = ucllcl1.get(0);
		double LCL1  = ucllcl1.get(1);
		double AVG1  = ucllcl1.get(2);
		
		// 불량률 각월 1일~말일 
		List<Double> ucllcl2 = NelsonRulesProcessor.getUclLcl(numArray2);
		double UCL2 = ucllcl2.get(0);
		double LCL2  = ucllcl2.get(1);
		double AVG2  = ucllcl2.get(2);
		
		NelsonRulesProcessor underProductionRule = new NelsonRulesProcessor(
																UCL1, LCL1, 
																underProductionSetting.get(1).getLength(), 
																underProductionSetting.get(2).getLength(), 
																underProductionSetting.get(3).getLength(), 
																underProductionSetting.get(4).getLength(), 
																underProductionSetting.get(4).getLimit(), 
																underProductionSetting.get(5).getLength(), 
																underProductionSetting.get(5).getLimit(), 
																underProductionSetting.get(6).getLength(), 
																underProductionSetting.get(7).getLength(),
																underProductionSetting.get(0).getIschecked(),
																underProductionSetting.get(1).getIschecked(), 
																underProductionSetting.get(2).getIschecked(), 
																underProductionSetting.get(3).getIschecked(), 
																underProductionSetting.get(4).getIschecked(), 
																underProductionSetting.get(5).getIschecked(), 
																underProductionSetting.get(6).getIschecked(), 
																underProductionSetting.get(7).getIschecked()
													);
		
		NelsonRulesProcessor defectRateRule = new NelsonRulesProcessor(
																UCL2, LCL2, 
																defectRateSetting.get(1).getLength(), 
																defectRateSetting.get(2).getLength(), 
																defectRateSetting.get(3).getLength(), 
																defectRateSetting.get(4).getLength(), 
																defectRateSetting.get(4).getLimit(), 
																defectRateSetting.get(5).getLength(), 
																defectRateSetting.get(5).getLimit(), 
																defectRateSetting.get(6).getLength(), 
																defectRateSetting.get(7).getLength(),
																defectRateSetting.get(0).getIschecked(),
																defectRateSetting.get(1).getIschecked(), 
																defectRateSetting.get(2).getIschecked(), 
																defectRateSetting.get(3).getIschecked(), 
																defectRateSetting.get(4).getIschecked(), 
																defectRateSetting.get(5).getIschecked(), 
																defectRateSetting.get(6).getIschecked(), 
																defectRateSetting.get(7).getIschecked()
													);
		
		String prodResult = "";
		
		underProductionRule.setEvent(numArray1);
		if(underProductionRule.rule1()) {
			prodResult += "RULE1, ";
		}
		
		if(underProductionRule.rule2()) {
			prodResult += "RULE2, ";
		}
		
		if(underProductionRule.rule3()) {
			prodResult += "RULE3, ";
		}
		
		if(underProductionRule.rule4()) {
			prodResult += "RULE4, ";
		}
		
		if(underProductionRule.rule5()) {
			prodResult += "RULE5, ";
		}
		
		if(underProductionRule.rule6()) {
			prodResult += "RULE6, ";
		}
		
		if(underProductionRule.rule7()) {
			prodResult += "RULE7, ";
		}
		
		if(underProductionRule.rule8()) {
			prodResult += "RULE8, ";
		}
		
		if(!"".equals(prodResult)) {
			prodResult = StringUtils.removeEnd(prodResult, ", ");
		}
		
		String failResult = "";
		
		defectRateRule.setEvent(numArray2);
		if(defectRateRule.rule1()) {
			failResult += "RULE1, ";
		}
		
		if(defectRateRule.rule2()) {
			failResult += "RULE2, ";
		}
		
		if(defectRateRule.rule3()) {
			failResult += "RULE3, ";
		}
		
		if(defectRateRule.rule4()) {
			failResult += "RULE4, ";
		}
		
		if(defectRateRule.rule5()) {
			failResult += "RULE5, ";
		}
		
		if(defectRateRule.rule6()) {
			failResult += "RULE6, ";
		}
		
		if(defectRateRule.rule7()) {
			failResult += "RULE7, ";
		}
		
		if(defectRateRule.rule8()) {
			failResult += "RULE8, ";
		}
		
		if(!"".equals(failResult)) {
			failResult = StringUtils.removeEnd(failResult, ", ");
		}
		
		NelsonResultDTO result = NelsonResultDTO.builder()
									.prodUcl(UCL1)
									.prodLcl(LCL1)
									.prodAvg(AVG1)
									.failUcl(UCL2)
									.failLcl(LCL2)
									.failAvg(AVG2)
									.prodResult(prodResult)
									.failResult(failResult)
									.dateList(dateList)
									.prodAvgList(numArray1)
									.failAvgList(numArray2)
									.build();
		return result;
	}
}
