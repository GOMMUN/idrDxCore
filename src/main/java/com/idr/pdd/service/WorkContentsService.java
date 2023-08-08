package com.idr.pdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idr.pdd.vo.WorkContents;
import com.idr.pdd.dto.WorkContentsDTO;
import com.idr.pdd.mapper.WorkContentsMapper;
import java.util.List;

@Service
public class WorkContentsService{
	
	@Autowired
	WorkContentsMapper mapper;
	
	public int countByTid(String tid) throws Exception {
		return mapper.countByTid(tid);
	}
	
	@Transactional
	public int create(WorkContents param, int dataseq) throws Exception {
		
		WorkContentsDTO result = WorkContentsDTO.builder()
										.worktimeFrom(param.getFromtime())
										.worktimeTo(param.getTotime())
										.manhour(param.getManhour())
										.prodQty(param.getProdqty())
										.firsttimeGoodQty(param.getFirstgoodqty())
										.firsttimeFailQty(param.getFirstfailqty())
										.reworkGoodQty(param.getReworkgoodqty())
										.reworkFailQty(param.getReworkfailqty())
										.tid(param.getTid())
										.workdailySeq(dataseq)
										.build();
		return mapper.create(result);
	}
	
	@Transactional
	public int create(List<WorkContents> param, int dataseq) throws Exception {
		int result=0;
		for(WorkContents workcontent:param) {
			WorkContentsDTO dto = WorkContentsDTO.builder()
					.worktimeFrom(workcontent.getFromtime())
					.worktimeTo(workcontent.getTotime())
					.manhour(workcontent.getManhour())
					.prodQty(workcontent.getProdqty())
					.firsttimeGoodQty(workcontent.getFirstgoodqty())
					.firsttimeFailQty(workcontent.getFirstfailqty())
					.reworkGoodQty(workcontent.getReworkgoodqty())
					.reworkFailQty(workcontent.getReworkfailqty())
					.tid(workcontent.getTid())
					.workdailySeq(dataseq)
					.build();
			result+=mapper.create(dto);
		}
		
		return result;
	}
	
	public int sumProdQtyBySeq(int dataSeq) throws Exception{
		return mapper.sumProdQtyBySeq(dataSeq);
	}
}
