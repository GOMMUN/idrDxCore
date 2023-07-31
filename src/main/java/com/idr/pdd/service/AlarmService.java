package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.common.BlockKitDataParshing;
import com.idr.pdd.common.SendBlockit;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.exception.MessageSendException;
import com.idr.pdd.mapper.AlarmSettingMapper;
import com.idr.pdd.mapper.BlockKitMapper;
import com.idr.pdd.mapper.FactoryMapper;
import com.idr.pdd.mapper.MaterialMapper;
import com.idr.pdd.mapper.WorkContentsMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;
import com.idr.pdd.vo.WorkContents;

@Service
public class AlarmService {
	
	private static final String OCCUR = "OCCUR";		// 발생
	private static final String NOTICE = "NOTICE";		// 통보
	private static final String CONFIRM = "CONFIRM";	// 확인
	
	private static final String UNDER_PRODUCTION = "UNDER-PRODUCTION";				// 계획대비 생산량 부족 (납기지연)
	private static final String NOTOPERATE_PRESS = "NOTOPERATE-PRESS";				// 프레스 설비 작동 이상 (설비이상)
	private static final String FIRST_ARTICLE_CAUTION = "FIRST-ARTICLE-CAUTION";	// 초도품 확인-이상사전감지 (품질/물류이상)
	
	@Autowired
	AlarmSettingMapper alarmSettingMapper;
	
	@Autowired
	BlockKitMapper blockKitMapper;
	
	@Autowired
	FactoryMapper factoryMapper;
	
	@Autowired
	MaterialMapper materialMapper;
	
	@Autowired
	WorkDailyReportMapper workDailyReportMapper;
	
	@Autowired
	WorkContentsMapper workContentsMapper;
	
	public boolean plantCheck(String plant) {
		if("KEM".equals(plant)) {
			return true;
		}else {
			return false;
		}
	}
	
	// 협력사 한테 알람 보내기
	// 작업내용 용 발생
	public void occur(WorkDailyReportDTO parent) throws Exception {
								
		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();
		
		// 생산량 총 합산
		int prodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);
		
		// 백분율
		int percent = 0;
		
		if(planQty == 0 || prodQty == 0) {
			percent = 0;
		}else {
			percent = (int)((double) prodQty / (double) planQty * 100);
		}
		
		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);
		
		// 설정값보다 백분율이 작을경우 알람 발생
		if(value > percent) {
			String plantName = factoryMapper.findName(parent.getFactoryid());
			String materialName = materialMapper.findName(parent.getMaterialid());
			
			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
			String btnString = "통보";
			String btnUrl = BlockKitDataParshing.setUnderProductionOccurUrl(parent.getFactoryid(), parent.getMaterialid(), planQty, prodQty, percent);			
			
			String message = BlockKitDataParshing.underProduction(blockKit, btnString, btnUrl, plantName, materialName, planQty, prodQty, percent);
			
			String botId = "RXNW84AM9BC7KDK8";
			String botToken = SendBlockit.BlockitToken(botId);
			
			if(botToken == null) {
				throw new MessageSendException();
			}else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);
			}
		}
	}
	
	// 대표기업한테 알람보내기
	// 작업내용 용 통보
	public void notice(WorkDailyReportDTO parent) throws Exception {
		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();
		
		
		
		// 계획대비 생산량 부족 알람
		// 생산량 총 합산
		int prodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);
		
		// 백분율
		int percent = 0;
		
		if(planQty == 0 || prodQty == 0) {
			percent = 0;
		}else {
			percent = (int)((double) prodQty / (double) planQty * 100);
		}
		
		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);
		
		// 설정값보다 백분율이 작을경우 알람 발생
		if(value > percent) {
			String plantName = factoryMapper.findName(parent.getFactoryid());
			String materialName = materialMapper.findName(parent.getMaterialid());
			
			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
			String btnString = "확인";
			String btnUrl = BlockKitDataParshing.setUnderProductionNoticeUrl(parent.getFactoryid(), parent.getMaterialid(), planQty, prodQty, percent);		
			
			String message = BlockKitDataParshing.underProduction(blockKit, btnString, btnUrl, plantName, materialName, planQty, prodQty, percent);
			
			String botId = "RXNW84AM9BC7KDK8";
			String botToken = SendBlockit.BlockitToken(botId);
			
			if(botToken == null) {
				throw new MessageSendException();
			}else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);
			}
		}
		
		// 불량률 알람
	}

	private void underProduction(WorkContents param) throws Exception {
		
		
		WorkDailyReportDTO dataSeqPlanQty = workDailyReportMapper.findDataseqPlanQty(WorkDailyReportDTO.builder()
																						.factoryid(param.getPlant())
																						.lineid(param.getLine())
																						.workDate(param.getDate())
																						.shiftid(param.getShift())
																						.materialid(param.getMaterial())
																						.modelid(param.getModel()).build()
																	);
		
		int workDailySeq = dataSeqPlanQty.getDataseq();
		int planQty = dataSeqPlanQty.getPlanQty();
		
		// 생산량 총 합산
		int prodQty = 0;
		
		// 백분율
		int percent = (int)((double) prodQty / (double) planQty * 100);
		
		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);
		
		// 설정값보다 백분율이 작을경우 알람 발생
		if(value > percent) {
			
			String plantName = factoryMapper.findName(param.getPlant());
			
			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
			
			//String result = BlockKitDataParshing.underProduction(OCCUR, blockKit, plantName, planQty, prodQty, percent);
			
			if("KEM".equals(param.getPlant())) {
				// 통보 > 확인 ( 대표기업 )
			}else {
				// 발생 > 통보 > 확인 ( 협럽사 )
			}
		}
	}
	
	
	
}
