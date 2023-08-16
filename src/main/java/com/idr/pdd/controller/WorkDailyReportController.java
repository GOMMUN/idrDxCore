package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import com.idr.pdd.service.JobexechistService;
import com.idr.pdd.service.WorkDailyReportService;
import com.idr.pdd.vo.WorkDailyReport;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("workdaily-report")
@Tag(name = "1. 작업일보", description = "WORKDAILY_REPORT")
public class WorkDailyReportController {

	@Autowired
	private WorkDailyReportService service;

	@Autowired
	private JobexechistService jobexechistService;

//	@Transactional
//	@ResponseBody
//	@PostMapping("/")
//	@Operation(summary = "등록", description = "작업일보를 신규 등록합니다.", responses = {
//			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Message.class))),
//			@ApiResponse(responseCode = "400", description = "BAD_REQUEST") })
//	public ResponseEntity<Message> create(@RequestBody WorkDailyReport param) {
//
//		Message message = new Message();
//		HttpHeaders headers = new HttpHeaders();
//
//		try {
//
//			if (!CheckUtils.isValidation(param)) {
//				throw new ValidationException("필수값 입력해주세요.");
//			}
//
//			if (service.countByTid(param.getTid()) > 0) {
//				throw new ValidationException("동일한 TID 존재");
//			}
//
//			if (service.countByWorkDailyReport(param) > 0) {
//				throw new ValidationException("동일한 작업일보 존재");
//			}
//
//			int result = service.create(param);
//
//			jobexechistService.create(param.getTid(), "Create",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//			jobexechistService.save(param.getTid(), "Create",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//
//			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//			message.setStatus(StatusEnum.OK.getCode());
//			message.setMessage(StatusEnum.OK.getName());
//			message.setData(result);
//
//			jobexechistService.create(param.getTid(), "Complited",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//			jobexechistService.save(param.getTid(), "Complited",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//
//			return new ResponseEntity<>(message, headers, HttpStatus.OK);
//		} catch (Exception e) {
//
//			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
//			message.setMessage(e.getMessage());
//			message.setData(null);
//
//			jobexechistService.create(param.getTid(), "Failed",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//			jobexechistService.save(param.getTid(), "Failed",
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
//
//			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
//		}
//	}

	@ResponseBody
	@PostMapping("/")
//	@Operation(summary = "등록array", description = "작업일보를 신규 등록합니다.array", responses = {
	@Operation(summary = "등록", description = "작업일보를 신규 등록합니다.", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Message.class))),
			@ApiResponse(responseCode = "400", description = "BAD_REQUEST") })
	public ResponseEntity<Message> create(@RequestBody List<WorkDailyReport> params) {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			if (service.countByTid(params.get(0).getTid()) > 0) {
				throw new ValidationException("동일한 TID 존재");
			}
			
			int result = service.create(params);
			
			jobexechistService.create(params.get(0).getTid(), "Complited",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			jobexechistService.save(params.get(0).getTid(), "Complited",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			
			jobexechistService.create(params.get(0).getTid(), "Failed",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			jobexechistService.save(params.get(0).getTid(), "Failed",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

}
