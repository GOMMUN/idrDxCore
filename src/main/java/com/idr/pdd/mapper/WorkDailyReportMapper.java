package com.idr.pdd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.idr.pdd.dto.WorkDailyReportDTO;
import com.idr.pdd.dto.WorkerInputDTO;
import com.idr.pdd.vo.WorkerInput;

@Mapper
public interface WorkDailyReportMapper {

	WorkDailyReportDTO find(WorkDailyReportDTO dto) throws Exception;
	WorkDailyReportDTO findDataseqPlanQty(WorkDailyReportDTO param) throws Exception;
	
	int count(WorkDailyReportDTO dto) throws Exception;
	int countByTid(String tid) throws Exception;
	
	int create(WorkDailyReportDTO param) throws Exception;
}
