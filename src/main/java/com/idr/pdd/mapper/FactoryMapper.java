package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FactoryMapper {

	String findName(String id);
}
