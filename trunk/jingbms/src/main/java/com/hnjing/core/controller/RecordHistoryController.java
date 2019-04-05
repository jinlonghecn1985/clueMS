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
import com.hnjing.core.model.entity.RecordHistory;
import com.hnjing.core.service.RecordHistoryService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: RecordHistoryController
 * @Description: 操作记录HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@RestController
@Api(description="操作记录")
public class RecordHistoryController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private RecordHistoryService recordHistoryService;

	
	@ApiOperation(value = "新增 添加操作记录信息", notes = "添加操作记录信息")
	@RequestMapping(value = "/recordhistory", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addRecordHistory(HttpServletResponse response,
			@ApiParam(value = "recordHistory") @RequestBody RecordHistory recordHistory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		recordHistory.setOId(null);
		recordHistoryService.addRecordHistory(recordHistory);
		return recordHistory;
	}
	
	
	@ApiOperation(value = "更新 根据操作记录标识更新操作记录信息", notes = "根据操作记录标识更新操作记录信息")
	@RequestMapping(value = "/recordhistory/{oId:.+}", method = RequestMethod.PUT)
	public Object modifyRecordHistoryById(HttpServletResponse response,
			@PathVariable Integer oId,
			@ApiParam(value = "recordHistory", required = true) @RequestBody RecordHistory recordHistory
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		RecordHistory tempRecordHistory = recordHistoryService.queryRecordHistoryByOId(oId);
		recordHistory.setOId(oId);
		if(null == tempRecordHistory){
			throw new NotFoundException("操作记录");
		}
		return recordHistoryService.modifyRecordHistory(recordHistory);
	}

	@ApiOperation(value = "删除 根据操作记录标识删除操作记录信息", notes = "根据操作记录标识删除操作记录信息")
	@RequestMapping(value = "/recordhistory/{oId:.+}", method = RequestMethod.DELETE)
	public Object dropRecordHistoryByOId(HttpServletResponse response, @PathVariable Integer oId) {
		RecordHistory recordHistory = recordHistoryService.queryRecordHistoryByOId(oId);
		if(null == recordHistory){
			throw new NotFoundException("操作记录");
		}
		return recordHistoryService.dropRecordHistoryByOId(oId);
	}
	
	@ApiOperation(value = "查询 根据操作记录标识查询操作记录信息", notes = "根据操作记录标识查询操作记录信息")
	@RequestMapping(value = "/recordhistory/{oId:.+}", method = RequestMethod.GET)
	public Object queryRecordHistoryById(HttpServletResponse response,
			@PathVariable Integer oId) {
		RecordHistory recordHistory = recordHistoryService.queryRecordHistoryByOId(oId);
		if(null == recordHistory){
			throw new NotFoundException("操作记录");
		}
		return recordHistory;
	}
	
	@ApiOperation(value = "查询 根据操作记录属性查询操作记录信息列表", notes = "根据操作记录属性查询操作记录信息列表")
	@RequestMapping(value = "/recordhistory", method = RequestMethod.GET)
	public Object queryRecordHistoryList(HttpServletResponse response,
			RecordHistory recordHistory) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return recordHistoryService.queryRecordHistoryByProperty(ClassUtil.transBean2Map(recordHistory, false));
	}
	
	@ApiOperation(value = "查询分页 根据操作记录属性分页查询操作记录信息列表", notes = "根据操作记录属性分页查询操作记录信息列表")
	@RequestMapping(value = "/recordhistorys", method = RequestMethod.GET)
	public Object queryRecordHistoryPage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, RecordHistory recordHistory) {				
		return recordHistoryService.queryRecordHistoryForPage(pagenum, pagesize, sort, recordHistory);
	}

}
