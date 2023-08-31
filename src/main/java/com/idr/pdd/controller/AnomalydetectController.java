package com.idr.pdd.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.exception.MessageSendException;
import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.service.AnomalydetectService;
import com.idr.pdd.vo.Anomalydetect;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("anomalydetect")
@Tag(name = "8. 이상감지", description = "")
public class AnomalydetectController {

	@Autowired
	private AnomalydetectService service;
	
	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "이상감지를 신규 등록합니다.")
    public ResponseEntity<Message> create(@RequestBody Anomalydetect param) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
		try {
			if(!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}
			
			int result = 0 ;
			if(service.count(param) > 0) {
				throw new MessageSendException("동일한 알람 내역 존재");
			}else {
				result = service.create(param);
			}
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	        
	        message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(result);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
    }
	
	
	
}
