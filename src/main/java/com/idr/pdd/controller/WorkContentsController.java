package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.service.AlarmService;
import com.idr.pdd.service.WorkContentsService;
import com.idr.pdd.service.WorkDailyReportService;
import com.idr.pdd.service.WorkerInputService;
import com.idr.pdd.service.WorkerManhourService;
import com.idr.pdd.service.WorkerSupportService;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerManhour;
import com.idr.pdd.vo.WorkerSupport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("work-contents")
@Tag(name = "5. 작업내용", description = "WORK_COUNTENTS")
public class WorkContentsController {
	
	@Autowired
	private WorkDailyReportService pservice;
	
	@Autowired
	private WorkContentsService service;
	
	@Autowired
	private AlarmService alarmService;

	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "작업내용을 신규 등록합니다.")
    public ResponseEntity<Message> create(@RequestBody WorkContents param) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
		try {
			int dataseq = 0;
			
			if(!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			if(service.countByTid(param.getTid()) > 0) {
				throw new ValidationException("동일한 TID 존재");
			}
			
			WorkDailyReportDTO parent = pservice.find(param);
			
			if(parent == null) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			dataseq = parent.getDataseq();
			
			if(dataseq == 0) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}
			
			int result = service.create(param, dataseq);
			
			if("KEM".equals(param.getPlant())) {
				// 처음에 통보테이블에 insert
				// blockStr : UNDER-PRODUCTION, FIRST-ARTICLE-CAUTION
				// btnStr : 확인
				// btnUrl : 확인 테이블에 데이터를 넣을수 있는 api
			}else {
				// 처음에 발생테이블에 insert
				// blockStr : UNDER-PRODUCTION, FIRST-ARTICLE-CAUTION
				// btnStr : 통보
				// btnUrl : 통보 테이블에 데이터를 넣을수 있는 api
			}
			
			
			// 팀플리 메신저 연동 시작
			// 1. 보낼 데이터(blockKit 데이터 생성)
			// 1-1. blockStr : 계획대비 생산량부족, 불량품 가져오기
			// 1-2. btnStr : 파라미터로 들어오 공장명 KEM > 확인, 혈력사 > 통보
			// 1-3. btnUrl : 파라미터로 들어오 공장명 KEM > 알람 확인 테이블 넣을수있는 구조 , 혈력사 > 알람 통보 테이블에 넣을수 있는구조
			
			
			// 2. 팀플리에 메세지 전송
			// 2-1 토큰
			// 2-2 blockkitData 전송
			alarmService.underProduction(parent.getFactoryid(), parent.getPlanQty(), param.getTid());
			// 팀플리 메신저 연동 종료
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	        
	        message.setStatus(StatusEnum.OK);
	        message.setMessage(StatusEnum.OK.toString());
	        message.setData(result);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
}
