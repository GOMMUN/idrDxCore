package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerSupport;

import jakarta.xml.bind.ValidationException;

import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.dto.WorkerSupportDTO;
import com.idr.pdd.mapper.JobexechistMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;
import com.idr.pdd.mapper.WorkerSupportMapper;

@Service
public class WorkerSupportService {

	@Autowired
	WorkDailyReportMapper pmapper;
	
	@Autowired
	WorkerSupportMapper mapper;

	@Autowired
	JobexechistMapper jobMapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}

	private int create(WorkerSupport param, int dataseq) throws Exception {

		WorkerSupportDTO result = WorkerSupportDTO.builder().personid(param.getMan())
				.supporttimeFrom(param.getFromtime()).supporttimeTo(param.getTotime()).manhour(param.getManhour())
				.tid(param.getTid()).workdailySeq(dataseq).build();
		return mapper.create(result);
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public int create(List<WorkerSupport> params) throws Exception {
		
		int result = 0;
		
		for(WorkerSupport param : params) {
			
			int dataseq = 0;
			
			if (!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			WorkDailyReportDTO parent = find(param);
			
			if (parent == null) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			dataseq = parent.getDataseq();
			
			if (dataseq == 0) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			result += create(param,dataseq);
			
			jobexechist(params.get(0).getTid(), "Create",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
		}
		
		return result;
	}
	
	private WorkDailyReportDTO find(WorkerSupport param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return pmapper.find(result);
	}
	
	private void jobexechist(String tid, String state, String start, String error) {
		
		JobexechistDTO dto = new JobexechistDTO();
		dto.setExecTid(tid);
		dto.setExecStarttime(start);
		dto.setExecEndtime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")));
		dto.setState(state);
		dto.setMessageError(error);
		
		jobMapper.create(dto);
		jobMapper.save(dto);
	}
}
