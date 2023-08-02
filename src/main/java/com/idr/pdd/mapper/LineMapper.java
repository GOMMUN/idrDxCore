package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LineMapper {

	String findName(String id);
}
