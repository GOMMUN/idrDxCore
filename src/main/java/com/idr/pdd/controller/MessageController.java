package com.idr.pdd.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.BlockKitDataParshing;
import com.idr.pdd.common.BotId;
import com.idr.pdd.common.Message;
import com.idr.pdd.common.SendBlockit;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.exception.MessageSendException;
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
@RequestMapping("/message")
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
	@GetMapping("/underProduction/notice")
    public ResponseEntity<Message> underProductionNotice(String plant, String material, String tid, int planQty, int prodQty, int percent) {
		// 협력사 > 대표기업으로 알람
		// 통보 테이블에 insert
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	String plantName = factoryService.findName(plant);
        	String materialName = materialService.findName(material);
        	
        	String btnUrl = BlockKitDataParshing.setUnderProductionNoticeUrl(plant, material,tid, planQty, prodQty, percent);
        	
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
        	
        	String botId = "RXNW84AM9BC7KDK8";
//        	String botId = BotId.UNDER_PRODUCTION_MAIN.name();
			String botToken = SendBlockit.BlockitToken(botId);
			
			if(botToken == null) {
				throw new MessageSendException();
			}else {
				SendBlockit.BlockitMesaageSend(botId, botToken, messageParam);
				
				// 이상감지 알람 테이블에 INSERT
		        Anomalydetect anomalydetect = new Anomalydetect();
	        	
	        	anomalydetect.setFactory(plant);
	        	anomalydetect.setMessengerid(tid);
	        	anomalydetect.setMessengerReason("UNDER-PRODUCTION");
	        	anomalydetect.setMessengerReasondescription("생산계획 대비 생산량 부족");
	        	anomalydetect.setMessengerState("NOTICE");
	        	
	        	service.create(anomalydetect);
			}
	        
        	message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@GetMapping("/underProduction/confirm")
    public ResponseEntity<Message> underProductionConfirm(String plant, String tid) {
		// 확인 테이블에만 insert
		// 알람 x
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	Anomalydetect anomalydetect = new Anomalydetect();
        	
        	anomalydetect.setFactory(plant);
        	anomalydetect.setMessengerid(tid);
        	anomalydetect.setMessengerReason("UNDER-PRODUCTION");
        	anomalydetect.setMessengerReasondescription("생산계획 대비 생산량 부족");
        	anomalydetect.setMessengerState("CONFIRM");
        	
        	service.create(anomalydetect);
	        
	        message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@GetMapping("/defectRate/notice")
    public ResponseEntity<Message> defectRateNotice(String plant, String material, String tid, String prodDate, int failQty, int prodQty, int percent) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	String plantName = factoryService.findName(plant);
        	String materialName = materialService.findName(material);
        	
        	String btnUrl = BlockKitDataParshing.setDefectRateNoticeUrl(plant, material,tid, prodDate, failQty, prodQty, percent);
        	
        	//defectRate(String blockKit, String btnString, String btnUrl, String prodDate, String materialName, int failQty, int prodQty, int percent)
        	// 통보 > 확인
        	String messageParam = BlockKitDataParshing.defectRate(
        													blockKitService.find("DEFECT-RATE"), 
        													"확인", 
        													btnUrl,
        													prodDate, 
        													materialName, 
        													failQty, 
        													prodQty, 
        													percent
        												);
        	
        	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
        	
        	String botId = "RXNW84AM9BC7KDK8";
			String botToken = SendBlockit.BlockitToken(botId);
			
			if(botToken == null) {
				throw new MessageSendException();
			}else {
				SendBlockit.BlockitMesaageSend(botId, botToken, messageParam);
				
				// 이상감지 알람 테이블에 INSERT
		        Anomalydetect anomalydetect = new Anomalydetect();
	        	
	        	anomalydetect.setFactory(plant);
	        	anomalydetect.setMessengerid(tid);
	        	anomalydetect.setMessengerReason("DEFECT-RATE");
	        	anomalydetect.setMessengerReasondescription("불량율 알림");
	        	anomalydetect.setMessengerState("NOTICE");
	        	
	        	service.create(anomalydetect);
			}
	        
	        
        	
        	message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@GetMapping("/defectRate/confirm")
    public ResponseEntity<Message> defectRateConfirm(String plant, String tid) {
		
		Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        
        try {
        	
        	Anomalydetect anomalydetect = new Anomalydetect();
        	
        	anomalydetect.setFactory(plant);
        	anomalydetect.setMessengerid(tid);
        	anomalydetect.setMessengerReason("DEFECT-RATE");
        	anomalydetect.setMessengerReasondescription("불량율 알림");
        	anomalydetect.setMessengerState("CONFIRM");
        	
        	service.create(anomalydetect);
	        
	        message.setStatus(StatusEnum.OK.getCode());
	        message.setMessage(StatusEnum.OK.getName());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
			
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
	        message.setMessage(e.getMessage());
	        message.setData(null);
	        
	        return  new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
