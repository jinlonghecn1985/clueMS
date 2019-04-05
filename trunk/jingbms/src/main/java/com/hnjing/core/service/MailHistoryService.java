package com.hnjing.core.service;

import java.util.List;
import java.util.Map;


import com.hnjing.core.model.entity.MailHistory;

/**
 * @ClassName: MailHistory
 * @Description: 邮件发送记录服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public interface MailHistoryService {

    /**
	 * @Title: addMailHistory
	 * @Description:添加邮件发送记录
	 * @param mailHistory 实体
	 * @return Integer
	 */
	MailHistory addMailHistory(MailHistory mailHistory);
	
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
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param mailHistory 实体
	 * @return List<MailHistory>
	 */
	Map<String, Object> queryMailHistoryForPage(Integer pagenum, Integer pagesize, String sort, MailHistory mailHistory);
	 
	 /**
	 * @Title: queryMailHistoryByProperty
	 * @Description:根据属性查询邮件发送记录
	 * @return List<MailHistory>
	 */
	 List<MailHistory> queryMailHistoryByProperty(Map<String, Object> map);	 
	 
}
