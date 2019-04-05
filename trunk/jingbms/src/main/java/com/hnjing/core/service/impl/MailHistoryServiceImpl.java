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


import com.hnjing.core.model.entity.MailHistory;
import com.hnjing.core.model.dao.MailHistoryMapper;
import com.hnjing.core.service.MailHistoryService;

/**
 * @ClassName: MailHistory
 * @Description: 邮件发送记录服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Service("mailHistoryService")
@Transactional(readOnly=true)
public class  MailHistoryServiceImpl implements MailHistoryService {	
	private static final Logger logger = LoggerFactory.getLogger(MailHistoryServiceImpl.class);
	
	@Autowired
    private MailHistoryMapper mailHistoryMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addMailHistory
	 * @Description:添加邮件发送记录
	 * @param mailHistory 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public MailHistory addMailHistory(MailHistory mailHistory){
		int ret = mailHistoryMapper.addMailHistory(mailHistory);
		if(ret>0){
			return mailHistory;
		}
		return null;
	}
	
	/**
	 * @Title modifyMailHistory
	 * @Description:修改邮件发送记录
	 * @param mailHistory 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyMailHistory(MailHistory mailHistory){
		return mailHistoryMapper.modifyMailHistory(mailHistory);
	}
	
	/**
	 * @Title: dropMailHistoryById
	 * @Description:删除邮件发送记录
	 * @param id 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropMailHistoryById(Integer id){
		return mailHistoryMapper.dropMailHistoryById(id);
	}
	
	/**
	 * @Title: queryMailHistoryById
	 * @Description:根据实体标识查询邮件发送记录
	 * @param id 实体标识
	 * @return MailHistory
	 */
	@Override
	public MailHistory queryMailHistoryById(Integer id){
		return mailHistoryMapper.queryMailHistoryById(id);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryMailHistoryForPage
	 * @Description: 根据邮件发送记录属性与分页信息分页查询邮件发送记录信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param mailHistory 实体
	 * @return List<MailHistory>
	 */
	@Override
	public Map<String, Object> queryMailHistoryForPage(Integer pagenum, Integer pagesize, String sort, MailHistory mailHistory){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, MailHistory.class);
		List<MailHistory> entityList = mailHistoryMapper.queryMailHistoryForPage(pageBounds, mailHistory);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, MailHistory.class);
		}
		
		PageList<MailHistory> pagelist = (PageList<MailHistory>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryMailHistoryByProperty
	 * @Description:根据属性查询邮件发送记录
	 * @return List<MailHistory>
	 */
	@Override
	public List<MailHistory> queryMailHistoryByProperty(Map<String, Object> map){
		return mailHistoryMapper.queryMailHistoryByProperty(map);
	}


}
