package com.idr.pdd.common;

import com.idr.pdd.dto.AnomalydetectConfirmDTO;
import com.idr.pdd.dto.AnomalydetectNoticeDTO;
import com.idr.pdd.dto.AnomalydetectOccurDTO;
import com.idr.pdd.vo.NotoperateContents;
import com.idr.pdd.vo.RejectContents;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.vo.WorkDailyReport;
import com.idr.pdd.vo.WorkerInput;
import com.idr.pdd.vo.WorkerManhour;
import com.idr.pdd.vo.WorkerSupport;

public class CheckUtils {
	
	public static boolean isValidation(WorkDailyReport param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlanqty() == 0) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(WorkerInput param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getWorker().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(WorkerManhour param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getSeparation().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(WorkerSupport param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMan().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getFromtime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(WorkContents param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getFromtime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTotime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(NotoperateContents param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getFromtime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTotime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(RejectContents param) {
		boolean result = false;
		
		if(param.getDate().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getPlant().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getLine().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getShift().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getModel().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getMaterial().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOperation().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getRejectcode().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(AnomalydetectOccurDTO param) {
		boolean result = false;
		
		if(param.getFactoryid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurDatetime().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurUpdator().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurTarget().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurReason().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getOccurReasondescRiption().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		return result;
	}
	
	public static boolean isValidation(AnomalydetectNoticeDTO param) {
		boolean result = false;
		
		if(param.getFactoryid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getNoticeid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getNoticeTarget().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getNoticeReason().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		if(param.getNoticeUpdator().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}

		
		return result;
	}
	
	public static boolean isValidation(AnomalydetectConfirmDTO  param) {
		boolean result = false;
		
		if(param.getFactoryid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getConfirmid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getConfirmUpdator().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		
		if(param.getConfirmTarget().isEmpty()) {
			result = false;
		}else {
			result = true;
		}
		if(param.getTid().isEmpty()) {
			result = false;
		}else {
			result = true;
		}


		
		return result;
	}
}
