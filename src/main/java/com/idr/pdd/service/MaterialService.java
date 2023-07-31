package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.mapper.FactoryMapper;
import com.idr.pdd.mapper.MaterialMapper;

@Service
public class MaterialService {
	
	@Autowired
	private MaterialMapper mapper;

	public String findName(String id) {
		return mapper.findName(id);
	}
}
