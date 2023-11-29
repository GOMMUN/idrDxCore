package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.dto.NelsonResultDTO;
import com.idr.pdd.service.NelsonRuleService;
import com.idr.pdd.vo.WorkContents;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nelsonrule")
public class NelsonRuleController {

	@Autowired
	private NelsonRuleService nelsonService;
	
	@ResponseBody
	@GetMapping("/find")
	public ResponseEntity<Message> find(String workDate, String factoryid, String lineid, String materialid) {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			Map<String, String> params = new HashMap<>();
			
			params.put("workDate", workDate);
			params.put("factoryid", factoryid);
			params.put("materialid", materialid);
			
			// 넬슨 룰 조회
			NelsonResultDTO result = nelsonService.find(params);
			
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
}
