package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.MailHistory;

/**
 * @ClassName: MailHistoryMapper
 * @Description: 邮件发送记录映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Mapper
public interface MailHistoryMapper {

	/**
	 * @Title: addMailHistory
	 * @Description:添加邮件发送记录
	 * @param mailHistory 实体
	 * @return Integer
	 */
	Integer addMailHistory(MailHistory mailHistory);
	
	/**
	 * @Title modifyMailHistory
	 * @Description:修改邮件发送记录
	 * @param mailHistory 实体
	 * @return Integer
	 */
	Integer modifyMailHistory(MailHistory mailHistory);
	
	/**
	 * @Title: dropMailHistoryById
	 * @Description:删除邮件发送记录
	 * @param id 实体标识
	 * @return Integer
	 */
	Integer dropMailHistoryById(Integer id);
	
	/**
	 * @Title: queryMailHistoryById
	 * @Description:根据实体标识查询邮件发送记录
	 * @param id 实体标识
	 * @return MailHistory
	 */
	MailHistory queryMailHistoryById(Integer id);
	 
	/**
	 * @Title: queryMailHistoryForPage
	 * @Description: 根据邮件发送记录属性与分页信息分页查询邮件发送记录信息
	 * @param pageBounds 分页信息
	 * @param mailHistory 实体
	 * @return List<MailHistory>
	 */
	List<MailHistory> queryMailHistoryForPage(PageBounds pageBounds, @Param("mailHistory") MailHistory mailHistory);
	 
	 /**
	  * @Title: queryMailHistoryByProperty
	  * @Description:根据属性查询邮件发送记录
	  * @return List<MailHistory>
	  */
	 List<MailHistory> queryMailHistoryByProperty(@Param("mailHistory") Map<String, Object> map);
	 
	 
	 
}
