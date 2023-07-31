package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.mapper.BlockKitMapper;

@Service
public class BlockKitService {

	@Autowired
	private BlockKitMapper mapper;
	
	public String find(String type) {
		return mapper.find(type);
	}
}
