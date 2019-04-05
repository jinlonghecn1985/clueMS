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
import java.util.UUID;


import com.hnjing.core.model.entity.Dictionary;
import com.hnjing.core.model.dao.DictionaryMapper;
import com.hnjing.core.service.DictionaryService;

/**
 * @ClassName: Dictionary
 * @Description: 数据字典服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月14日 17时18分
 */
@Service("dictionaryService")
@Transactional(readOnly=true)
public class  DictionaryServiceImpl implements DictionaryService {	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
	
	@Autowired
    private DictionaryMapper dictionaryMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addDictionary
	 * @Description:添加数据字典
	 * @param dictionary 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Dictionary addDictionary(Dictionary dictionary){
		dictionary.setDicId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		int ret = dictionaryMapper.addDictionary(dictionary);
		if(ret>0){
			return dictionary;
		}
		return null;
	}
	
	/**
	 * @Title modifyDictionary
	 * @Description:修改数据字典
	 * @param dictionary 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyDictionary(Dictionary dictionary){
		return dictionaryMapper.modifyDictionary(dictionary);
	}
	
	/**
	 * @Title: dropDictionaryByDicId
	 * @Description:删除数据字典
	 * @param dicId 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropDictionaryByDicId(String dicId){
		return dictionaryMapper.dropDictionaryByDicId(dicId);
	}
	
	/**
	 * @Title: queryDictionaryByDicId
	 * @Description:根据实体标识查询数据字典
	 * @param dicId 实体标识
	 * @return Dictionary
	 */
	@Override
	public Dictionary queryDictionaryByDicId(String dicId){
		return dictionaryMapper.queryDictionaryByDicId(dicId);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryDictionaryForPage
	 * @Description: 根据数据字典属性与分页信息分页查询数据字典信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param dictionary 实体
	 * @return List<Dictionary>
	 */
	@Override
	public Map<String, Object> queryDictionaryForPage(Integer pagenum, Integer pagesize, String sort, Dictionary dictionary){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, Dictionary.class);
		List<Dictionary> entityList = dictionaryMapper.queryDictionaryForPage(pageBounds, dictionary);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, Dictionary.class);
		}
		
		PageList<Dictionary> pagelist = (PageList<Dictionary>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryDictionaryByProperty
	 * @Description:根据属性查询数据字典
	 * @return List<Dictionary>
	 */
	@Override
	public List<Dictionary> queryDictionaryByProperty(Map<String, Object> map){
		if(map==null) {
			map = new HashMap<String, Object>();
		}
		if(!map.containsKey("dicStatus")) {
			map.put("dicStatus", 0);
		}
		return dictionaryMapper.queryDictionaryByProperty(map);
	}


}
