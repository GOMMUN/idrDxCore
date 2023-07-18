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
import com.idr.pdd.dto.AnomalydetectNoticeDTO;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.service.AnomalydetectNoticeService;
import com.idr.pdd.service.AnomalydetectOccurService;
import com.idr.pdd.service.NotoperateContentsService;
import com.idr.pdd.service.WorkContentsService;
import com.idr.pdd.service.WorkDailyReportService;
import com.idr.pdd.service.WorkerInputService;
import com.idr.pdd.service.WorkerManhourService;
import com.idr.pdd.service.WorkerSupportService;
import com.idr.pdd.vo.NotoperateContents;
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
@RequestMapping("anomaly-notice")
@Tag(name = "9. 이상감지공지", description = "ANOMALYDETECT_NOTICE")
public class AnomalydetectNoticeController {
	

	@Autowired
	private AnomalydetectNoticeService service;

	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "이상감지공지를 신규 등록합니다.")
    public ResponseEntity<Message> create(@RequestBody AnomalydetectNoticeDTO param) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
		try {
			//int dataseq = 0;
			
			if(!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			
			int result = service.create(param);
			
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
