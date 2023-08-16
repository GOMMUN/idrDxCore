package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
import com.idr.pdd.service.NotoperateContentsService;
import com.idr.pdd.service.RejectContentsService;
import com.idr.pdd.service.WorkContentsService;
import com.idr.pdd.service.WorkDailyReportService;
import com.idr.pdd.service.WorkerInputService;
import com.idr.pdd.service.WorkerManhourService;
import com.idr.pdd.service.WorkerSupportService;
import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.RejectContents;
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
@RequestMapping("reject-contents")
@Tag(name = "6. 부적합내역", description = "REJECT_CONTENTS")
public class RejectContentsController {

	@Autowired
	private WorkDailyReportService pservice;

	@Autowired
	private RejectContentsService service;

	@Autowired
	private JobexechistService jobexechistService;

//	@ResponseBody
//	@PostMapping("/")
//	@Operation(summary = "등록", description = "비가동내역을 신규 등록합니다.")
//	public ResponseEntity<Message> create(@RequestBody RejectContents param) {
//
//		Message message = new Message();
//		HttpHeaders headers = new HttpHeaders();
//
//		try {
//			int dataseq = 0;
//
//			if (!CheckUtils.isValidation(param)) {
//				throw new ValidationException("필수값 입력해주세요.");
//			}
//
//			if (service.countByTid(param.getTid()) > 0) {
//				throw new ValidationException("동일한 TID 존재");
//			}
//
//			WorkDailyReportDTO parent = pservice.find(param);
//
//			if (parent == null) {
//				throw new ValidationException("작업일보가 존재하지 않습니다.");
//			}
//
//			dataseq = parent.getDataseq();
//
//			if (dataseq == 0) {
//				throw new ValidationException("작업일보가 존재하지 않습니다.");
//			}
//
//			int result = service.create(param, dataseq);
//
//			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//			message.setStatus(StatusEnum.OK.getCode());
//			message.setMessage(StatusEnum.OK.getName());
//			message.setData(result);
//
//			return new ResponseEntity<>(message, headers, HttpStatus.OK);
//		} catch (Exception e) {
//
//			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
//			message.setMessage(e.getMessage());
//			message.setData(null);
//
//			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
//		}
//	}

	@Transactional
	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "비가동내역을 신규 등록합니다.")
//	@Operation(summary = "등록array", description = "array비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> creates(@RequestBody List<RejectContents> param) {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		try {
			int dataseq = 0;
			
			if (service.countByTid(param.get(0).getTid()) > 0) {
				throw new ValidationException("동일한 TID 존재");
			}

			for (RejectContents rc : param) {
				if (!CheckUtils.isValidation(rc)) {
					throw new ValidationException("필수값 입력해주세요.");
				}

				WorkDailyReportDTO parent = pservice.find(rc);

				if (parent == null) {
					throw new ValidationException("작업일보가 존재하지 않습니다.");
				}

				dataseq = parent.getDataseq();

				if (dataseq == 0) {
					throw new ValidationException("작업일보가 존재하지 않습니다.");
				}
			}

			int result = service.create(param, dataseq);

			for (RejectContents rc : param) {
				jobexechistService.create(rc.getTid(), "Create",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
				jobexechistService.save(rc.getTid(), "Create",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			}
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			for (RejectContents rc : param) {
				jobexechistService.create(rc.getTid(), "Complited",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
				jobexechistService.save(rc.getTid(), "Complited",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			}

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			for (RejectContents rc : param) {
				jobexechistService.create(rc.getTid(), "Failed",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
				jobexechistService.save(rc.getTid(), "Failed",
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:SS")), null);
			}

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

}
