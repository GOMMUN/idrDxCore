package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.RejectContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.RejectContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.RejectContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;

@Service
public class RejectContentsService {

	@Autowired
	RejectContentsMapper mapper;

	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}

	@Transactional
	public int create(RejectContents param, int dataseq) throws Exception {

		RejectContentsDTO result = RejectContentsDTO.builder().rejectType(param.getRejecttype())
				.rejectItemid(param.getRejectcode()).firsttimeRejectQty(param.getFirstrejectqty())
				.reworkRejectQty(param.getReworkrejectqty()).tid(param.getTid()).workdailySeq(dataseq).build();
		return mapper.create(result);
	}

	@Transactional
	public int create(List<RejectContents> param, int dataseq) throws Exception {

		int result = 0;
		for (RejectContents rc : param) {
			RejectContentsDTO dto = RejectContentsDTO.builder().rejectType(rc.getRejecttype())
					.rejectItemid(rc.getRejectcode()).firsttimeRejectQty(rc.getFirstrejectqty())
					.reworkRejectQty(rc.getReworkrejectqty()).tid(rc.getTid()).workdailySeq(dataseq).build();
			result+=mapper.create(dto);
		}

		return result;
	}
}
