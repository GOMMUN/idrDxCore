package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.mapper.FactoryMapper;

@Service
public class FactoryService {
	
	@Autowired
	private FactoryMapper mapper;

	public String findName(String id) {
		return mapper.findName(id);
	}
}
