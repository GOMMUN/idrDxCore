package com.idr.pdd.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;

import jakarta.xml.bind.ValidationException;

import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.dto.WorkerInputDTO;
import com.idr.pdd.mapper.JobexechistMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;
import com.idr.pdd.mapper.WorkerInputMapper;

@Service
public class WorkerInputService{
	
	@Autowired
	WorkDailyReportMapper pmapper;
	
	@Autowired
	WorkerInputMapper mapper;
	
	@Autowired
	JobexechistMapper jobMapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(List<WorkerInput> params) throws Exception {
		
		int result=0;
		
		for(WorkerInput param : params) {
			
			int dataseq = 0;
			
			if (!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			WorkDailyReportDTO parent = find(param);
			
			if (parent == null) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			if (dataseq == 0) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			dataseq = parent.getDataseq();
			
			result += create(param,dataseq);
			
			jobexechist(params.get(0).getTid(), "Create",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
		}
		
		return result;
	}
	
	private WorkDailyReportDTO find(WorkerInput param) throws Exception {
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

	private int create(WorkerInput param, int dataseq) throws Exception {
		
		WorkerInputDTO result = WorkerInputDTO.builder()
										.personid(param.getWorker())
										.overtime(param.getOvertime())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
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
