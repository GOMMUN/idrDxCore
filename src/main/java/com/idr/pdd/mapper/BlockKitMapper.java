package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockKitMapper {

	String find(String type);
}
