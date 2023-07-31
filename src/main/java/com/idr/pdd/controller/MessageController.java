package com.idr.pdd.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.BlockKitDataParshing;
import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.service.AnomalydetectService;
import com.idr.pdd.service.BlockKitService;
import com.idr.pdd.service.FactoryService;
import com.idr.pdd.service.MaterialService;
import com.idr.pdd.vo.Anomalydetect;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("message")
@Tag(name = "9. 이상감지", description = "")
public class MessageController {
	
	@Autowired
	private AnomalydetectService service;
	
	@Autowired
	private BlockKitService blockKitService;
	
	@Autowired
	private FactoryService factoryService;
	
	@Autowired
	private MaterialService materialService;

	@ResponseBody
	@PostMapping("/underProduction/notice")
	@Operation(summary = "통보 > 확인", description = "통보 > 확인")
    public ResponseEntity<Message> underProductionNotice(String plant, String material, int planQty, int prodQty, int percent) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	String plantName = factoryService.findName(plant);
        	String materialName = materialService.findName(material);
        	
        	String btnUrl = BlockKitDataParshing.setUnderProductionOccurUrl(plant, material, planQty, prodQty, percent);
        	
        	// 통보 > 확인
        	String messageParam = BlockKitDataParshing.underProduction(
        													blockKitService.find("UNDER-PRODUCTION"), 
        													"확인", 
        													btnUrl,
        													plantName, 
        													materialName, 
        													planQty, 
        													prodQty, 
        													percent
        												);
        	
        	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	        
	        message.setStatus(StatusEnum.OK);
	        message.setMessage(StatusEnum.OK.toString());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@PostMapping("/underProduction/confirm")
	@Operation(summary = "통보 > 확인", description = "통보 > 확인")
    public ResponseEntity<Message> underProductionConfirm(String btnUrl, String plant, String material, String tid, int planQty, int prodQty, int percent) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	Anomalydetect anomalydetect = new Anomalydetect();
        	
        	anomalydetect.setFactory(plant);
        	anomalydetect.setMessengerid(tid);
        	anomalydetect.setMessengerReason("UNDER-PRODUCTION");
        	anomalydetect.setMessengerReasondescription("생산계획 대비 생산량 부족");
        	anomalydetect.setMessengerState("CONFIRM");
        	
        	service.create(null);
	        
	        message.setStatus(StatusEnum.OK);
	        message.setMessage(StatusEnum.OK.toString());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
