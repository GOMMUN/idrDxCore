package com.idr.pdd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.DateSumDTO;

@Mapper
public interface NelsonRuleMapper {

	void find();
	
	List<DateSumDTO> findByPlanSum(Map<String, String> params);
	List<DateSumDTO> findByProdSum(Map<String, String> params);
	List<DateSumDTO> findByFailSum(Map<String, String> params);
}
