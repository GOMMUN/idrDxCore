package com.idr.pdd.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.WorkContents;

import jakarta.xml.bind.ValidationException;

import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.JobexechistDTO;
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.mapper.JobexechistMapper;
import com.idr.pdd.mapper.NotoperateContentsMapper;
import com.idr.pdd.mapper.WorkContentsMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;

@Service
public class NotoperateContentsService{
	
	@Autowired
	WorkDailyReportMapper pmapper;
	
	@Autowired
	NotoperateContentsMapper mapper;
	
	@Autowired
	JobexechistMapper jobMapper;
	
	@Autowired
	private AlarmService alarmService;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	private int create(NotoperateContents param, int dataseq) throws Exception {
		
		NotoperateContentsDTO result = NotoperateContentsDTO.builder()
										.notoperatetimeFrom(param.getFromtime())
										.notoperatetimeTo(param.getTotime())
										.hands(param.getMan())
										.manhour(param.getManhour())
										.cause(param.getContentcause())
										.correctiveAction(param.getCorrectiveaction())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
	
	@Transactional
	public int create(List<NotoperateContents> params) throws Exception {
		
		int result = 0;
		
		for(NotoperateContents param : params) {
			
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
			
			if (!alarmService.plantCheck(parent.getFactoryid())) {
				alarmService.occur(parent, param);
				
				jobexechist(params.get(0).getTid(), "Send",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			} else {
				alarmService.notice(parent, param);
				
				jobexechist(params.get(0).getTid(), "Send",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			}
		}
		return result;
	}
	
	private WorkDailyReportDTO find(NotoperateContents param) throws Exception {
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
