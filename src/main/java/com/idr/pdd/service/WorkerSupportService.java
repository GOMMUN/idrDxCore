package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.idr.pdd.vo.WorkerSupport;
import com.idr.pdd.dto.WorkerSupportDTO;
import com.idr.pdd.mapper.WorkerSupportMapper;

@Service
public class WorkerSupportService {

	@Autowired
	WorkerSupportMapper mapper;

	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}

	@Transactional
	public int create(WorkerSupport param, int dataseq) throws Exception {

		WorkerSupportDTO result = WorkerSupportDTO.builder().personid(param.getMan())
				.supporttimeFrom(param.getFromtime()).supporttimeTo(param.getTotime()).manhour(param.getManhour())
				.tid(param.getTid()).workdailySeq(dataseq).build();
		return mapper.create(result);
	}

	@Transactional
	public int create(List<WorkerSupport> param, int dataseq) throws Exception {
		int result = 0;
		for (WorkerSupport ws : param) {
			WorkerSupportDTO dto = WorkerSupportDTO.builder().personid(ws.getMan())
					.supporttimeFrom(ws.getFromtime()).supporttimeTo(ws.getTotime()).manhour(ws.getManhour())
					.tid(ws.getTid()).workdailySeq(dataseq).build();
			result+=mapper.create(dto);
		}

		return result;
	}
}
