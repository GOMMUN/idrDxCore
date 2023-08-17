package com.idr.pdd.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.RejectContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerManhour;
import com.idr.pdd.vo.WorkerSupport;
import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.exception.ValidationException;
import com.idr.pdd.mapper.JobexechistMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;

@Service
public class WorkDailyReportService{

	@Autowired
	WorkDailyReportMapper mapper;
	
	@Autowired
	JobexechistMapper jobMapper;
	
	public WorkDailyReportDTO find(WorkDailyReport param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(WorkerInput param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(WorkerManhour param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(WorkerSupport param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(WorkContents param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(NotoperateContents param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}
	
	public WorkDailyReportDTO find(RejectContents param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.find(result);
	}

	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	public int countByWorkDailyReport(WorkDailyReport param) throws Exception {
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
				.workDate(param.getDate())
				.factoryid(param.getPlant())
				.lineid(param.getLine())
				.shiftid(param.getShift())
				.modelid(param.getModel())
				.materialid(param.getMaterial())
				.build();
		
		return mapper.count(result);
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public int create(List<WorkDailyReport> params) throws Exception {

		int result = 0;
		
		for (WorkDailyReport param : params) {
			
			if (!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			if (countByWorkDailyReport(param) > 0) {
				throw new ValidationException("동일한 작업일보 존재");
			}
			
			result += create(param);
		
			jobexechist(params.get(0).getTid(), "Create",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
		}
		
		return result;
	}
	
	private int create(WorkDailyReport param) throws Exception {
		
		WorkDailyReportDTO result = WorkDailyReportDTO.builder()
										.workDate(param.getDate())
										.factoryid(param.getPlant())
										.lineid(param.getLine())
										.shiftid(param.getShift())
										.modelid(param.getModel())
										.materialid(param.getMaterial())
										.planQty(param.getPlanqty())
										.register(param.getRegister())
										.notes(param.getNotes())
										.tid(param.getTid())
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
