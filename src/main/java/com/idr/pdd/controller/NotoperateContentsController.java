package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.idr.pdd.dto.NotoperateContentsDTO;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.service.AlarmService;
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
@RequestMapping("notoperate-contents")
@Tag(name = "7. 비가동내역", description = "NOTOPERATE_CONTENTS")
public class NotoperateContentsController {

	@Autowired
	private WorkDailyReportService pservice;

	@Autowired
	private NotoperateContentsService service;

	@Autowired
	private AlarmService alarmService;
	
	@ResponseBody
	@PostMapping("/")
	@Operation(summary = "등록", description = "비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> create(@RequestBody NotoperateContents param) {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		try {
			int dataseq = 0;

			if (!CheckUtils.isValidation(param)) {
				throw new ValidationException("필수값 입력해주세요.");
			}

			if (service.countByTid(param.getTid()) > 0) {
				throw new ValidationException("동일한 TID 존재");
			}

			WorkDailyReportDTO parent = pservice.find(param);

			if (parent == null) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}

			dataseq = parent.getDataseq();

			if (dataseq == 0) {
				throw new ValidationException("작업일보가 존재하지 않습니다.");
			}

			int result = service.create(param, dataseq);

			// 알람 보낼 공장 체크
			if (!alarmService.plantCheck(param.getPlant())) {
				alarmService.occur(parent, param);
			} else {
				alarmService.notice(parent, param);
			}

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	/*
	@ResponseBody
	@PostMapping("/array")
	@Operation(summary = "등록array", description = "array비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> creates(@RequestBody List<NotoperateContents> param) {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		String tid = createTid(8).toUpperCase() + "-" + createTid(4).toUpperCase() + "-" + createTid(4).toUpperCase() +
				"-" + createTid(4).toUpperCase() + "-" + createTid(16).toUpperCase();
		try {
			WorkDailyReportDTO parent = null;
			int dataseq = 0;
			for (NotoperateContents notpercontent : param) {
				if (!CheckUtils.isValidation(notpercontent)) {
					throw new ValidationException("필수값 입력해주세요.");
				}

				if (service.countByTid(notpercontent.getTid()) > 0) {
					throw new ValidationException("동일한 TID 존재");
				}

				parent = pservice.find(notpercontent);

				if (parent == null) {
					throw new ValidationException("작업일보가 존재하지 않습니다.");
				}

				dataseq = parent.getDataseq();

				if (dataseq == 0) {
					throw new ValidationException("작업일보가 존재하지 않습니다.");
				}
			}

			int result = service.create(param, dataseq);



			// 알람 보낼 공장 체크
			if (!alarmService.plantCheck(param.get(0).getPlant())) {
				alarmService.occur(parent, param, tid);

			} else {
				alarmService.notice(parent, param, tid);

				
			}

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);


			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

	public String createTid(int num) {
		Random rnd = new Random();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < num; i++) {

			// rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한
			// 숫자를 StringBuffer 에 append 한다.

			if (rnd.nextBoolean()) {

				buf.append((char) ((int) (rnd.nextInt(26)) + 97));

			} else {

				buf.append((rnd.nextInt(10)));

			}

		}
		return buf.toString();
	}
*/

}
