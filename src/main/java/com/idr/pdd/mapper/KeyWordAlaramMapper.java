package com.idr.pdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.FairProd;

import java.util.List;

@Mapper
public interface KeyWordAlaramMapper {

	
	List<String> rank(String plant);
	
	List<FairProd> palaram(String line);
	
	List<FairProd> qalaram(String plant);

	List<FairProd> calaram(String line);
}
