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
import com.idr.pdd.vo.NotoperateContents;
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
@RequestMapping("/keywordalarm")
public class KeyWordAlarm {

	@Autowired
	private AlarmService alarmService;

	@ResponseBody
	@GetMapping("/palram")
	public ResponseEntity<Message> palram(@RequestParam("plant") String plant) throws Exception {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		// 알람 보낼 공장 체크

			alarmService.palram(plant);

		try {
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseBody
	@GetMapping("/qalram")
	public ResponseEntity<Message> qalram(@RequestParam("plant") String plant) throws Exception {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		// 알람 보낼 공장 체크

			alarmService.qalram(plant);

		try {
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@GetMapping("/calram")
	public ResponseEntity<Message> calram(@RequestParam("plant") String plant) throws Exception {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		// 알람 보낼 공장 체크

			alarmService.calram(plant);

		try {
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
}
