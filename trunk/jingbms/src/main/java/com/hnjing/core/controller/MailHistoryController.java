package com.hnjing.core.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hnjing.config.validation.BeanValidator;
import com.hnjing.config.web.exception.NotFoundException;
import com.hnjing.core.model.entity.MailHistory;
import com.hnjing.core.service.MailHistoryService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: MailHistoryController
 * @Description: 邮件发送记录HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@RestController
@Api(description="邮件发送记录")
public class MailHistoryController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private MailHistoryService mailHistoryService;

	
	@ApiOperation(value = "新增 添加邮件发送记录信息", notes = "添加邮件发送记录信息")
	@RequestMapping(value = "/mailhistory", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addMailHistory(HttpServletResponse response,
			@ApiParam(value = "mailHistory") @RequestBody MailHistory mailHistory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		mailHistory.setId(null);
		mailHistoryService.addMailHistory(mailHistory);
		return mailHistory;
	}
	
	
	@ApiOperation(value = "更新 根据邮件发送记录标识更新邮件发送记录信息", notes = "根据邮件发送记录标识更新邮件发送记录信息")
	@RequestMapping(value = "/mailhistory/{id:.+}", method = RequestMethod.PUT)
	public Object modifyMailHistoryById(HttpServletResponse response,
			@PathVariable Integer id,
			@ApiParam(value = "mailHistory", required = true) @RequestBody MailHistory mailHistory
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MailHistory tempMailHistory = mailHistoryService.queryMailHistoryById(id);
		mailHistory.setId(id);
		if(null == tempMailHistory){
			throw new NotFoundException("邮件发送记录");
		}
		return mailHistoryService.modifyMailHistory(mailHistory);
	}

	@ApiOperation(value = "删除 根据邮件发送记录标识删除邮件发送记录信息", notes = "根据邮件发送记录标识删除邮件发送记录信息")
	@RequestMapping(value = "/mailhistory/{id:.+}", method = RequestMethod.DELETE)
	public Object dropMailHistoryById(HttpServletResponse response, @PathVariable Integer id) {
		MailHistory mailHistory = mailHistoryService.queryMailHistoryById(id);
		if(null == mailHistory){
			throw new NotFoundException("邮件发送记录");
		}
		return mailHistoryService.dropMailHistoryById(id);
	}
	
	@ApiOperation(value = "查询 根据邮件发送记录标识查询邮件发送记录信息", notes = "根据邮件发送记录标识查询邮件发送记录信息")
	@RequestMapping(value = "/mailhistory/{id:.+}", method = RequestMethod.GET)
	public Object queryMailHistoryById(HttpServletResponse response,
			@PathVariable Integer id) {
		MailHistory mailHistory = mailHistoryService.queryMailHistoryById(id);
		if(null == mailHistory){
			throw new NotFoundException("邮件发送记录");
		}
		return mailHistory;
	}
	
	@ApiOperation(value = "查询 根据邮件发送记录属性查询邮件发送记录信息列表", notes = "根据邮件发送记录属性查询邮件发送记录信息列表")
	@RequestMapping(value = "/mailhistory", method = RequestMethod.GET)
	public Object queryMailHistoryList(HttpServletResponse response,
			MailHistory mailHistory) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return mailHistoryService.queryMailHistoryByProperty(ClassUtil.transBean2Map(mailHistory, false));
	}
	
	@ApiOperation(value = "查询分页 根据邮件发送记录属性分页查询邮件发送记录信息列表", notes = "根据邮件发送记录属性分页查询邮件发送记录信息列表")
	@RequestMapping(value = "/mailhistorys", method = RequestMethod.GET)
	public Object queryMailHistoryPage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, MailHistory mailHistory) {				
		return mailHistoryService.queryMailHistoryForPage(pagenum, pagesize, sort, mailHistory);
	}

}
