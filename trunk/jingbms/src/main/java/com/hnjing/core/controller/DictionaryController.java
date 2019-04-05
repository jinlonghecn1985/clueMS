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
import com.hnjing.core.model.entity.Dictionary;
import com.hnjing.core.service.DictionaryService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: DictionaryController
 * @Description: 数据字典HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@RestController
@Api(description="数据字典")
public class DictionaryController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private DictionaryService dictionaryService;

	
	@ApiOperation(value = "新增 添加数据字典信息", notes = "添加数据字典信息")
	@RequestMapping(value = "/dictionary", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addDictionary(HttpServletResponse response,
			@ApiParam(value = "dictionary") @RequestBody Dictionary dictionary) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		dictionary.setDicId(null);
		dictionaryService.addDictionary(dictionary);
		return dictionary;
	}
	
	
	@ApiOperation(value = "更新 根据数据字典标识更新数据字典信息", notes = "根据数据字典标识更新数据字典信息")
	@RequestMapping(value = "/dictionary/{dicId:.+}", method = RequestMethod.PUT)
	public Object modifyDictionaryById(HttpServletResponse response,
			@PathVariable String dicId,
			@ApiParam(value = "dictionary", required = true) @RequestBody Dictionary dictionary
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Dictionary tempDictionary = dictionaryService.queryDictionaryByDicId(dicId);
		dictionary.setDicId(dicId);
		if(null == tempDictionary){
			throw new NotFoundException("数据字典");
		}
		return dictionaryService.modifyDictionary(dictionary);
	}

	@ApiOperation(value = "删除 根据数据字典标识删除数据字典信息", notes = "根据数据字典标识删除数据字典信息")
	@RequestMapping(value = "/dictionary/{dicId:.+}", method = RequestMethod.DELETE)
	public Object dropDictionaryByDicId(HttpServletResponse response, @PathVariable String dicId) {
		Dictionary dictionary = dictionaryService.queryDictionaryByDicId(dicId);
		if(null == dictionary){
			throw new NotFoundException("数据字典");
		}
		return dictionaryService.dropDictionaryByDicId(dicId);
	}
	
	@ApiOperation(value = "查询 根据数据字典标识查询数据字典信息", notes = "根据数据字典标识查询数据字典信息")
	@RequestMapping(value = "/dictionary/{dicId:.+}", method = RequestMethod.GET)
	public Object queryDictionaryById(HttpServletResponse response,
			@PathVariable String dicId) {
		Dictionary dictionary = dictionaryService.queryDictionaryByDicId(dicId);
		if(null == dictionary){
			throw new NotFoundException("数据字典");
		}
		return dictionary;
	}
	
	@ApiOperation(value = "查询 根据数据字典属性查询数据字典信息列表", notes = "根据数据字典属性查询数据字典信息列表")
	@RequestMapping(value = "/dictionary", method = RequestMethod.GET)
	public Object queryDictionaryList(HttpServletResponse response,
			Dictionary dictionary) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return dictionaryService.queryDictionaryByProperty(ClassUtil.transBean2Map(dictionary, false));
	}
	
	@ApiOperation(value = "查询分页 根据数据字典属性分页查询数据字典信息列表", notes = "根据数据字典属性分页查询数据字典信息列表")
	@RequestMapping(value = "/dictionarys", method = RequestMethod.GET)
	public Object queryDictionaryPage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, Dictionary dictionary) {				
		return dictionaryService.queryDictionaryForPage(pagenum, pagesize, sort, dictionary);
	}

}
