package com.hnjing.core.service;

import java.util.List;
import java.util.Map;


import com.hnjing.core.model.entity.RecordHistory;

/**
 * @ClassName: RecordHistory
 * @Description: 操作记录服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public interface RecordHistoryService {

    /**
	 * @Title: addRecordHistory
	 * @Description:添加操作记录
	 * @param recordHistory 实体
	 * @return Integer
	 */
	RecordHistory addRecordHistory(RecordHistory recordHistory);
	
	/**
	 * @Title modifyRecordHistory
	 * @Description:修改操作记录
	 * @param recordHistory 实体
	 * @return Integer
	 */
	Integer modifyRecordHistory(RecordHistory recordHistory);
	
	/**
	 * @Title: dropRecordHistoryByOId
	 * @Description:删除操作记录
	 * @param oId 实体标识
	 * @return Integer
	 */
	Integer dropRecordHistoryByOId(Integer oId);
	
	/**
	 * @Title: queryRecordHistoryByOId
	 * @Description:根据实体标识查询操作记录
	 * @param oId 实体标识
	 * @return RecordHistory
	 */
	RecordHistory queryRecordHistoryByOId(Integer oId);
	 
	/**
	 * @Title: queryRecordHistoryForPage
	 * @Description: 根据操作记录属性与分页信息分页查询操作记录信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param recordHistory 实体
	 * @return List<RecordHistory>
	 */
	Map<String, Object> queryRecordHistoryForPage(Integer pagenum, Integer pagesize, String sort, RecordHistory recordHistory);
	 
	 /**
	 * @Title: queryRecordHistoryByProperty
	 * @Description:根据属性查询操作记录
	 * @return List<RecordHistory>
	 */
	 List<RecordHistory> queryRecordHistoryByProperty(Map<String, Object> map);	 
	 
}
