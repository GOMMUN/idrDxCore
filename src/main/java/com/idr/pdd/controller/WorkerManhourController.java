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
import com.idr.pdd.service.WorkDailyReportService;
import com.idr.pdd.service.WorkerInputService;
import com.idr.pdd.service.WorkerManhourService;
import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerManhour;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("worker-manhour")
@Tag(name = "3. 공수투입현황", description = "WORKER_MANHOUR")
public class WorkerManhourController {
	
	@Autowired
	private WorkDailyReportService pservice;
	
	@Autowired
	private WorkerManhourService service;

	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "공수투입현황을 신규 등록합니다.")
    public ResponseEntity<Message> create(@RequestBody WorkerManhour param) {
		
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
