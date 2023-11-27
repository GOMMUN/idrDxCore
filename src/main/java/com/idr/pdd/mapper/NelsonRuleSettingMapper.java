package com.idr.pdd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.NelsonruleSettingDTO;

@Mapper
public interface NelsonRuleSettingMapper {

	List<NelsonruleSettingDTO> findAll();
	List<NelsonruleSettingDTO> findByAlarmType(String alarmType);
}
