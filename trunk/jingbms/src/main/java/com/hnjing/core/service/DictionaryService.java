package com.hnjing.core.service;

import java.util.List;
import java.util.Map;


import com.hnjing.core.model.entity.Dictionary;

/**
 * @ClassName: Dictionary
 * @Description: 数据字典服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public interface DictionaryService {

    /**
	 * @Title: addDictionary
	 * @Description:添加数据字典
	 * @param dictionary 实体
	 * @return Integer
	 */
	Dictionary addDictionary(Dictionary dictionary);
	
	/**
	 * @Title modifyDictionary
	 * @Description:修改数据字典
	 * @param dictionary 实体
	 * @return Integer
	 */
	Integer modifyDictionary(Dictionary dictionary);
	
	/**
	 * @Title: dropDictionaryByDicId
	 * @Description:删除数据字典
	 * @param dicId 实体标识
	 * @return Integer
	 */
	Integer dropDictionaryByDicId(String dicId);
	
	/**
	 * @Title: queryDictionaryByDicId
	 * @Description:根据实体标识查询数据字典
	 * @param dicId 实体标识
	 * @return Dictionary
	 */
	Dictionary queryDictionaryByDicId(String dicId);
	 
	/**
	 * @Title: queryDictionaryForPage
	 * @Description: 根据数据字典属性与分页信息分页查询数据字典信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param dictionary 实体
	 * @return List<Dictionary>
	 */
	Map<String, Object> queryDictionaryForPage(Integer pagenum, Integer pagesize, String sort, Dictionary dictionary);
	 
	 /**
	 * @Title: queryDictionaryByProperty
	 * @Description:根据属性查询数据字典
	 * @return List<Dictionary>
	 */
	 List<Dictionary> queryDictionaryByProperty(Map<String, Object> map);	 
	 
}
