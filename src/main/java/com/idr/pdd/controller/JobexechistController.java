package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.CheckUtils;
import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.service.JobexechistService;

import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/log")
public class JobexechistController {

	@Autowired
	private JobexechistService service;
	
	@ResponseBody
	@PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody String tid, @RequestBody String state) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
		try {
			
			service.create(tid, state, LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			service.save(tid, state, LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	        
	        message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(null);
	        
	        service.create(tid, "Complited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
	        service.save(tid, "Complited", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        service.create(tid, "Failed", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
	        service.save(tid, "Failed", LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
}
