package com.idr.pdd.service;

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
import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.dto.WorkerInputDTO;
import com.idr.pdd.mapper.WorkDailyReportMapper;

@Service
public class WorkDailyReportService{

	@Autowired
	WorkDailyReportMapper mapper;
	
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
	
	@Transactional
	public int create(WorkDailyReport param) throws Exception {
		
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
	
	@Transactional
	public int create(List<WorkDailyReport> param) throws Exception {
		
		int result = 0;
		
		for (WorkDailyReport workDailyReport : param) {
			WorkDailyReportDTO dto = WorkDailyReportDTO.builder()
					.workDate(workDailyReport.getDate())
					.factoryid(workDailyReport.getPlant())
					.lineid(workDailyReport.getLine())
					.shiftid(workDailyReport.getShift())
					.modelid(workDailyReport.getModel())
					.materialid(workDailyReport.getMaterial())
					.register(workDailyReport.getRegister())
					.tid(workDailyReport.getTid())
					.build();
			result += mapper.create(dto);
		}
		return result;
	}
	

}
