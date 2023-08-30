package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idr.pdd.vo.WorkContents;

import jakarta.xml.bind.ValidationException;

import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.mapper.JobexechistMapper;
import com.idr.pdd.mapper.WorkContentsMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkContentsService{
	
	@Autowired
	WorkDailyReportMapper pmapper;
	
	@Autowired
	WorkContentsMapper mapper;
	
	@Autowired
	JobexechistMapper jobMapper;
	
	@Autowired
	private AlarmService alarmService;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	private int create(WorkContents param, int dataseq) throws Exception {
		
		WorkContentsDTO result = WorkContentsDTO.builder()
										.worktimeFrom(param.getFromtime())
										.worktimeTo(param.getTotime())
										.manhour(param.getManhour())
										.prodQty(param.getProdqty())
										.firsttimeGoodQty(param.getFirstgoodqty())
										.firsttimeFailQty(param.getFirstfailqty())
										.reworkGoodQty(param.getReworkgoodqty())
										.reworkFailQty(param.getReworkfailqty())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.notes(param.getNotes())
										.build();
		return mapper.create(result);
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public int create(List<WorkContents> params) throws Exception {
		
		int result = 0;
		
		List<WorkDailyReportDTO> parentList = new ArrayList<>();
		
		for(WorkContents param : params) {
			
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
			
			if(parentList.size() == 0) { 
				parentList.add(parent);
			}else if(parentList.size() > 0) {
				for (WorkDailyReportDTO obj : parentList) {
					if( parent.getDataseq() != obj.getDataseq()) {
						parentList.add(parent);
					}
				}
			}
			
			result += create(param,dataseq);
			
			jobexechist(params.get(0).getTid(), "Create",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
		}
		
		if(parentList.size() > 0) {
			for (WorkDailyReportDTO obj : parentList) {
				if (!alarmService.plantCheck(obj.getFactoryid())) {
					alarmService.occur(obj, params.get(0).getTid());
					
					jobexechist(params.get(0).getTid(), "Send",
							LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
				} else {
					alarmService.notice(obj, params.get(0).getTid());
					
					jobexechist(params.get(0).getTid(), "Send",
							LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
				}
			}
		}
		
		return result;
	}
	
	private WorkDailyReportDTO find(WorkContents param) throws Exception {
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
	
	public int sumProdQtyBySeq(int dataSeq) throws Exception{
		return mapper.sumProdQtyBySeq(dataSeq);
	}
}
