package com.hnjing.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnjing.utils.Constant;
import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.utils.paginator.domain.PageList;
import com.hnjing.utils.paginator.domain.PageService;


import com.hnjing.core.model.entity.RecordHistory;
import com.hnjing.core.model.dao.RecordHistoryMapper;
import com.hnjing.core.service.RecordHistoryService;

/**
 * @ClassName: RecordHistory
 * @Description: 操作记录服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Service("recordHistoryService")
@Transactional(readOnly=true)
public class  RecordHistoryServiceImpl implements RecordHistoryService {	
	private static final Logger logger = LoggerFactory.getLogger(RecordHistoryServiceImpl.class);
	
	@Autowired
    private RecordHistoryMapper recordHistoryMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addRecordHistory
	 * @Description:添加操作记录
	 * @param recordHistory 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public RecordHistory addRecordHistory(RecordHistory recordHistory){
		int ret = recordHistoryMapper.addRecordHistory(recordHistory);
		if(ret>0){
			return recordHistory;
		}
		return null;
	}
	
	/**
	 * @Title modifyRecordHistory
	 * @Description:修改操作记录
	 * @param recordHistory 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyRecordHistory(RecordHistory recordHistory){
		return recordHistoryMapper.modifyRecordHistory(recordHistory);
	}
	
	/**
	 * @Title: dropRecordHistoryByOId
	 * @Description:删除操作记录
	 * @param oId 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropRecordHistoryByOId(Integer oId){
		return recordHistoryMapper.dropRecordHistoryByOId(oId);
	}
	
	/**
	 * @Title: queryRecordHistoryByOId
	 * @Description:根据实体标识查询操作记录
	 * @param oId 实体标识
	 * @return RecordHistory
	 */
	@Override
	public RecordHistory queryRecordHistoryByOId(Integer oId){
		return recordHistoryMapper.queryRecordHistoryByOId(oId);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryRecordHistoryForPage
	 * @Description: 根据操作记录属性与分页信息分页查询操作记录信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param recordHistory 实体
	 * @return List<RecordHistory>
	 */
	@Override
	public Map<String, Object> queryRecordHistoryForPage(Integer pagenum, Integer pagesize, String sort, RecordHistory recordHistory){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, RecordHistory.class);
		List<RecordHistory> entityList = recordHistoryMapper.queryRecordHistoryForPage(pageBounds, recordHistory);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, RecordHistory.class);
		}
		
		PageList<RecordHistory> pagelist = (PageList<RecordHistory>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryRecordHistoryByProperty
	 * @Description:根据属性查询操作记录
	 * @return List<RecordHistory>
	 */
	@Override
	public List<RecordHistory> queryRecordHistoryByProperty(Map<String, Object> map){
		return recordHistoryMapper.queryRecordHistoryByProperty(map);
	}


}
