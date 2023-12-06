package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.idr.pdd.common.BlockKitDataParshing;
import com.idr.pdd.common.BotId;
import com.idr.pdd.common.SendBlockit;
import com.idr.pdd.common.Tid;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.dto.FairProd;
import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.exception.MessageSendException;
import com.idr.pdd.mapper.AlarmSettingMapper;
import com.idr.pdd.mapper.AnomalydetectNoticeMapper;
import com.idr.pdd.mapper.AnomalydetectOccurMapper;
import com.idr.pdd.mapper.BlockKitMapper;
import com.idr.pdd.mapper.FactoryMapper;
import com.idr.pdd.mapper.KeyWordAlaramMapper;
import com.idr.pdd.mapper.LineMapper;
import com.idr.pdd.mapper.MaterialMapper;
import com.idr.pdd.mapper.WorkContentsMapper;
import com.idr.pdd.mapper.WorkDailyReportMapper;
import com.idr.pdd.vo.Anomalydetect;
import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.WorkContents;

@Service
public class AlarmService {

	private static final String OCCUR = "OCCUR"; // 발생
	private static final String NOTICE = "NOTICE"; // 통보
	private static final String CONFIRM = "CONFIRM"; // 확인

	private static final String UNDER_PRODUCTION = "UNDER-PRODUCTION"; // 계획대비 생산량 부족 (납기지연)
	private static final String NOTOPERATE_PRESS = "NOTOPERATE-PRESS"; // 프레스 설비 작동 이상 (설비이상)
	private static final String FIRST_ARTICLE_CAUTION = "FIRST-ARTICLE-CAUTION"; // 초도품 확인-이상사전감지 (품질/물류이상)
	private static final String DEFECT_RATE = "DEFECT-RATE"; // 불량률 알림

	@Autowired
	AlarmSettingMapper alarmSettingMapper;

	@Autowired
	BlockKitMapper blockKitMapper;

	@Autowired
	FactoryMapper factoryMapper;

	@Autowired
	MaterialMapper materialMapper;

	@Autowired
	LineMapper lineMapper;

	@Autowired
	WorkDailyReportMapper workDailyReportMapper;

	@Autowired
	WorkContentsMapper workContentsMapper;

	@Autowired
	AnomalydetectOccurMapper occurMapper;

	@Autowired
	AnomalydetectNoticeMapper noticeMapper;

	@Autowired
	KeyWordAlaramMapper keywordalarammapper;
	
	public boolean plantCheck(String plant) {
		if ("KEM".equals(plant)) {
			return true;
		} else {
			return false; 
		}
	}
	
	// 협력사 한테 알람 보내기
	// 작업내용 용 발생
	public void occur(WorkDailyReportDTO parent, String tid) throws Exception {

		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();

		////////////////////////////////////////////////////////
		//////////////////// 생산대비 생산률 알람 //////////////////////
		////////////////////////////////////////////////////////
		// 생산량 총 합산
//		int prodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);
		int firsttimeGoodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);
		
		// 백분율
		int percent = 0;

		if (planQty == 0 || firsttimeGoodQty == 0) {
			percent = 0;
		} else {
			percent = (int) ((double) firsttimeGoodQty / (double) planQty * 100);
		}

		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);

		// 설정값보다 백분율이 작을경우 알람 발생
		if (value >= percent) {

			String plant = parent.getFactoryid();
			String plantName = factoryMapper.findName(plant);

			String material = parent.getMaterialid();
			String materialName = materialMapper.findName(material);

			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
			String btnString = "통보";
			
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setUnderProductionOccurUrl(plant, material, tid, messageTid, planQty, firsttimeGoodQty,
					percent);

			String message = BlockKitDataParshing.underProduction(blockKit, btnString, btnUrl, plantName, materialName,
					planQty, firsttimeGoodQty, percent);

			String botId = BotId.UNDER_PRODUCTION_VENDOR.getBot();
//				String botId = BotId.UNDER_PRODUCTION_VENDOR.name();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				AnomalydetectOccurDTO dto = AnomalydetectOccurDTO.builder().factoryid(plant).occurid(tid).tid(tid)
						.occurReason(UNDER_PRODUCTION).occurReasondescRiption("생산계획 대비 생산량 부족")
						.ea1(planQty)
						.ea2(firsttimeGoodQty)
						.value(percent)
						.build();

				if (occurMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					occurMapper.create(dto);
				}
			}
		}

		////////////////////////////////////////////////////////
		/////////////////////// 불량률 알람 ////////////////////////
		///////////////////////////////////////////////////////
		int firsttimeFailQty = workContentsMapper.sumFirsttimeFailQty(dataSeq);
		
		int prodQty = firsttimeGoodQty + firsttimeFailQty;
		
		// 설정값
		int value2 = alarmSettingMapper.find(DEFECT_RATE);

		// 백분율
		int percent2 = 0;

		if (firsttimeFailQty == 0 || prodQty == 0) {
			percent2 = 0;
		} else {
			percent2 = (int) ((double) firsttimeFailQty / (double) prodQty * 100);
		}

		// 설정값보다 백분율이 작을경우 알람 발생
		if (value2 <= percent2) {
			String plant = parent.getFactoryid();

			String prodDate = parent.getWorkDate();

			String material = parent.getMaterialid();
			String materialName = materialMapper.findName(material);

			// blockkit message
			String blockKit = blockKitMapper.find(DEFECT_RATE);
			String btnString = "통보";
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setDefectRateOccurUrl(plant, material, tid, messageTid, prodDate, firsttimeFailQty,
					prodQty, percent2);
			// String btnUrl = "https://www.daum.net/";

			String message = BlockKitDataParshing.defectRate(blockKit, btnString, btnUrl, prodDate, materialName,
					firsttimeFailQty, prodQty, percent2);

			String botId = BotId.DEFECT_RATE_VENDOR.getBot();
//				String botId = BotId.UNDER_PRODUCTION_MAIN.name();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				// 이상감지 알람 테이블에 INSERT
				AnomalydetectOccurDTO dto = AnomalydetectOccurDTO.builder().factoryid(plant).occurid(messageTid).tid(tid)
						.occurReason(DEFECT_RATE).occurReasondescRiption("불량율 알림")
						.ea1(prodQty)
						.ea2(firsttimeFailQty)
						.value(percent2)
						.build();

				if (occurMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					occurMapper.create(dto);
				}

			}
		}
	}

	// 비가동 내역 알람
	// 설비 이상 알랑 발생
	public void occur(WorkDailyReportDTO parent, NotoperateContents param) throws Exception {

		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();
		
		//List<NotoperateContents> NotoperateContentsDTOList = 
		
		// 설정값
		int value = alarmSettingMapper.find(NOTOPERATE_PRESS);
		
		// 비가동 알람 활성화
		if(value == 1) {
			String plant = parent.getFactoryid();
			String plantName = factoryMapper.findName(plant);

			String line = parent.getLineid();
			String lineName = lineMapper.findName(line);

			String getdate = parent.getWorkDate().substring(4, 6) + '-' + parent.getWorkDate().substring(6) + " "
					+ param.getFromtime().substring(0, 2) + ':' + param.getFromtime().substring(2, 4) + ':'
					+ param.getFromtime().substring(4);

			String tid = param.getTid();

			// blockkit message
			String blockKit = blockKitMapper.find(NOTOPERATE_PRESS);
			String btnString = "통보";
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setNotOperatepressOccurUrl(plant, line, getdate, tid, messageTid);
			String message = null;
			try {
				message = BlockKitDataParshing.notoperatePress(blockKit, btnString, btnUrl, plantName, lineName, getdate);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String botId = BotId.NOTOPERATE_PRESS_VENDOR.getBot();
//				String botId = BotId.UNDER_PRODUCTION_VENDOR.name();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				// 이상감지 알람 테이블에 INSERT
				Anomalydetect anomalydetect = new Anomalydetect();

				AnomalydetectOccurDTO dto = AnomalydetectOccurDTO.builder().factoryid(plant).occurid(messageTid).tid(tid)
						.occurReason(NOTOPERATE_PRESS).occurReasondescRiption("프레스 설비 작동 이상").build();

				if (occurMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					occurMapper.create(dto);
				}
			}
		}
	}

	// 대표기업한테 알람보내기
	// 작업내용 용 통보
	public void notice(WorkDailyReportDTO parent, String tid) throws Exception {
		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();

		////////////////////////////////////////////////////////
		//////////////////// 생산대비 생산률 알람 //////////////////////
		////////////////////////////////////////////////////////
		// 생산량 총 합산
//		int prodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);
		int firsttimeGoodQty = workContentsMapper.sumProdQtyBySeq(dataSeq);

		// 백분율
		int percent = 0;

		if (planQty == 0 || firsttimeGoodQty == 0) {
			percent = 0;
		} else {
			percent = (int) ((double) firsttimeGoodQty / (double) planQty * 100);
		}

		// 설정값
		int value = alarmSettingMapper.find(UNDER_PRODUCTION);

		// 설정값보다 백분율이 작을경우 알람 발생
		if (value >= percent) {
			String plant = parent.getFactoryid();
			String plantName = factoryMapper.findName(plant);

			String material = parent.getMaterialid();
			String materialName = materialMapper.findName(material);

			// blockkit message
			String blockKit = blockKitMapper.find(UNDER_PRODUCTION);
			String btnString = "확인";
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setUnderProductionNoticeUrl(plant, material, tid, messageTid, planQty, firsttimeGoodQty,
					percent);
			// String btnUrl = "https://www.daum.net/";

			String message = BlockKitDataParshing.underProduction(blockKit, btnString, btnUrl, plantName, materialName,
					planQty, firsttimeGoodQty, percent);

			String botId = BotId.UNDER_PRODUCTION_MAIN.getBot();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				// 이상감지 알람 테이블에 INSERT
				AnomalydetectNoticeDTO dto = AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(messageTid).tid(tid)
						.noticeReason(UNDER_PRODUCTION).noticeReasondescRiption("생산계획 대비 생산량 부족")
						.ea1(planQty)
						.ea2(firsttimeGoodQty)
						.value(percent)
						.build();

				if (noticeMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					noticeMapper.create(dto);
				}
			}
		}

		////////////////////////////////////////////////////////
		/////////////////////// 불량률 알람 ////////////////////////
		///////////////////////////////////////////////////////
		int firsttimeFailQty = workContentsMapper.sumFirsttimeFailQty(dataSeq);
		int prodQty = firsttimeGoodQty + firsttimeFailQty;

		// 설정값
		int value2 = alarmSettingMapper.find(DEFECT_RATE);

		// 백분율
		int percent2 = 0;

		if (firsttimeFailQty == 0 || prodQty == 0) {
			percent2 = 0;
		} else {
			percent2 = (int) ((double) firsttimeFailQty / (double) prodQty * 100);
		}

		// 설정값보다 백분율이 작을경우 알람 발생
		if (value2 <= percent2) {
			String plant = parent.getFactoryid();

			String prodDate = parent.getWorkDate();

			String material = parent.getMaterialid();
			String materialName = materialMapper.findName(material);

			// blockkit message
			String blockKit = blockKitMapper.find(DEFECT_RATE);
			String btnString = "확인";
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setDefectRateNoticeUrl(plant, material, tid, messageTid, prodDate,
					firsttimeFailQty, prodQty, percent2);
			// String btnUrl = "https://www.daum.net/";

			String message = BlockKitDataParshing.defectRate(blockKit, btnString, btnUrl, prodDate, materialName,
					firsttimeFailQty, prodQty, percent2);

			String botId = BotId.DEFECT_RATE_MAIN.getBot();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				// 이상감지 알람 테이블에 INSERT
				AnomalydetectNoticeDTO dto = AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(messageTid).tid(tid)
						.noticeReason(DEFECT_RATE).noticeReasondescRiption("불량율 알림")
						.ea1(prodQty)
						.ea2(firsttimeFailQty)
						.value(percent2)
						.build();

				if (noticeMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					noticeMapper.create(dto);
				}
			}
		}
	}

	// 비가동 내역 알람
	// 설비 이상 알랑 통보
	public void notice(WorkDailyReportDTO parent, NotoperateContents param) throws Exception {

		int dataSeq = parent.getDataseq();
		int planQty = parent.getPlanQty();
		
		// 설정값
		int value = alarmSettingMapper.find(NOTOPERATE_PRESS);
		
		// 비가동 알람 활성화
		if(value == 1) {
			String plant = parent.getFactoryid();
			String plantName = factoryMapper.findName(plant);

			String line = parent.getLineid();
			String lineName = lineMapper.findName(line);

			String getdate = parent.getWorkDate().substring(4, 6) + '-' + parent.getWorkDate().substring(6) + " "
					+ param.getFromtime().substring(0, 2) + ':' + param.getFromtime().substring(2, 4) + ':'
					+ param.getFromtime().substring(4);

			String tid = param.getTid();

			// blockkit message
			String blockKit = blockKitMapper.find(NOTOPERATE_PRESS);
			String btnString = "확인";
			String messageTid = Tid.generate();
			String btnUrl = BlockKitDataParshing.setNotOperatepressNoticeUrl(plant, line, getdate, tid, messageTid);
			String message = BlockKitDataParshing.notoperatePress(blockKit, btnString, btnUrl, plantName, lineName,
					getdate);

			String botId = BotId.NOTOPERATE_PRESS_MAIN.getBot();
//				String botId = BotId.UNDER_PRODUCTION_VENDOR.name();
			String botToken = SendBlockit.BlockitToken(botId);

			if (botToken == null) {
				throw new MessageSendException();
			} else {
				SendBlockit.BlockitMesaageSend(botId, botToken, message);

				AnomalydetectNoticeDTO dto = AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(messageTid).tid(tid)
						.noticeReason(NOTOPERATE_PRESS).noticeReasondescRiption("프레스 설비 작동 이상").build();

				if (noticeMapper.count(dto) > 0) {
					throw new MessageSendException("동일한 알람 내역 존재");
				} else {
					noticeMapper.create(dto);
				}
			}
		}
	}

	// =====================키워드 알람=================================
	public void palram(String plant) throws Exception {

		List<String> rank = keywordalarammapper.rank(plant);

		List<String> lineList = new ArrayList<>();
		List<List<FairProd>> finalresult = new ArrayList<>();

		if (rank.size() > 4) {
			lineList.add(rank.get(0));
			lineList.add(rank.get(1));
			lineList.add(rank.get(rank.size() - 2));
			lineList.add(rank.get(rank.size() - 1));
			finalresult.add(keywordalarammapper.palaram(lineList.get(0)));
			finalresult.add(keywordalarammapper.palaram(lineList.get(1)));
			finalresult.add(keywordalarammapper.palaram(lineList.get(2)));
			finalresult.add(keywordalarammapper.palaram(lineList.get(3)));
		} else {
			for (String line : rank) {
				finalresult.add(keywordalarammapper.palaram(line));
			}
		}

		String blockKit = blockKitMapper.find("P-ALARAM");
		// String btnString = "확인";

		String message = BlockKitDataParshing.palaram(blockKit, finalresult);

		String botId = BotId.PALAM_MAIN.getBot();

		String botToken = SendBlockit.BlockitToken(botId);

		if (botToken == null) {
			throw new MessageSendException();
		} else {
			SendBlockit.BlockitMesaageSend(botId, botToken, message);

			// AnomalydetectNoticeDTO dto =
			// AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(tid).tid(tid)
			// .noticeReason(NOTOPERATE_PRESS).noticeReasondescRiption("프레스 설비 작동
			// 이상").build();

		}
	}

	public void qalram(String plant) throws Exception {

		List<FairProd> result = keywordalarammapper.qalaram(plant);

		String blockKit = blockKitMapper.find("Q-ALARAM");
		// String btnString = "확인";

		String message = BlockKitDataParshing.qalaram(blockKit, result);

		String botId = BotId.QALAM_MAIN.getBot();

		String botToken = SendBlockit.BlockitToken(botId);

		if (botToken == null) {
			throw new MessageSendException();
		} else {
			SendBlockit.BlockitMesaageSend(botId, botToken, message);

			// AnomalydetectNoticeDTO dto =
			// AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(tid).tid(tid)
			// .noticeReason(NOTOPERATE_PRESS).noticeReasondescRiption("프레스 설비 작동
			// 이상").build();

		}

	}

	public void calram(String plant) throws Exception {

		List<String> rank = keywordalarammapper.rank(plant);

		List<String> lineList = new ArrayList<>();
		List<List<FairProd>> finalresult = new ArrayList<>();

		if (rank.size() > 4) {
			lineList.add(rank.get(0));
			lineList.add(rank.get(1));
			lineList.add(rank.get(rank.size() - 2));
			lineList.add(rank.get(rank.size() - 1));
			finalresult.add(keywordalarammapper.calaram(lineList.get(0)));
			finalresult.add(keywordalarammapper.calaram(lineList.get(1)));
			finalresult.add(keywordalarammapper.calaram(lineList.get(2)));
			finalresult.add(keywordalarammapper.calaram(lineList.get(3)));
		} else {
			for (String line : rank) {
				finalresult.add(keywordalarammapper.calaram(line));
			}
		}

		String blockKit = blockKitMapper.find("C-ALARAM");
		// String btnString = "확인";

		String message = BlockKitDataParshing.calaram(blockKit, finalresult);

		String botId = BotId.CALAM_MAIN.getBot();

		String botToken = SendBlockit.BlockitToken(botId);

		if (botToken == null) {
			throw new MessageSendException();
		} else {
			SendBlockit.BlockitMesaageSend(botId, botToken, message);
		}

	}

	public void dalram(String plant) throws Exception {

		FairProd result = keywordalarammapper.dalaram(plant);

		String blockKit = blockKitMapper.find("D-ALARAM");
		// String btnString = "확인";

		String message = BlockKitDataParshing.dalaram(blockKit, result);

		String botId = BotId.DALAM_MAIN.getBot();

		String botToken = SendBlockit.BlockitToken(botId);

		if (botToken == null) {
			throw new MessageSendException();
		} else {
			SendBlockit.BlockitMesaageSend(botId, botToken, message);

			// AnomalydetectNoticeDTO dto =
			// AnomalydetectNoticeDTO.builder().factoryid(plant).noticeid(tid).tid(tid)
			// .noticeReason(NOTOPERATE_PRESS).noticeReasondescRiption("프레스 설비 작동
			// 이상").build();

		}
	}
}
