package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.RecordHistory;

/**
 * @ClassName: RecordHistoryMapper
 * @Description: 操作记录映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Mapper
public interface RecordHistoryMapper {

	/**
	 * @Title: addRecordHistory
	 * @Description:添加操作记录
	 * @param recordHistory 实体
	 * @return Integer
	 */
	Integer addRecordHistory(RecordHistory recordHistory);
	
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
	 * @param pageBounds 分页信息
	 * @param recordHistory 实体
	 * @return List<RecordHistory>
	 */
	List<RecordHistory> queryRecordHistoryForPage(PageBounds pageBounds, @Param("recordHistory") RecordHistory recordHistory);
	 
	 /**
	  * @Title: queryRecordHistoryByProperty
	  * @Description:根据属性查询操作记录
	  * @return List<RecordHistory>
	  */
	 List<RecordHistory> queryRecordHistoryByProperty(@Param("recordHistory") Map<String, Object> map);
	 
	 
	 
}
