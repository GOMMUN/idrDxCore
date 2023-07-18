package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmSettingMapper {

	int find(String type) throws Exception;
}
