package com.hnjing.core.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hnjing.core.model.entity.ClueInfo;

/**
 * @ClassName: ClueInfo
 * @Description: 线索信息服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public interface ClueInfoService {

    /**
	 * @Title: addClueInfo
	 * @Description:添加线索信息
	 * @param clueInfo 实体
	 * @return Integer
	 */
	ClueInfo addClueInfo(ClueInfo clueInfo);
	
	/**
	 * @Title modifyClueInfo
	 * @Description:修改线索信息
	 * @param clueInfo 实体
	 * @return Integer
	 */
	Integer modifyClueInfo(ClueInfo clueInfo);
	
	/**
	 * @Title: dropClueInfoByCId
	 * @Description:删除线索信息
	 * @param cId 实体标识
	 * @return Integer
	 */
	Integer dropClueInfoByCId(Integer cId);
	
	/**
	 * @Title: queryClueInfoByCId
	 * @Description:根据实体标识查询线索信息
	 * @param cId 实体标识
	 * @return ClueInfo
	 */
	ClueInfo queryClueInfoByCId(Integer cId);
	 
	/**
	 * @Title: queryClueInfoForPage
	 * @Description: 根据线索信息属性与分页信息分页查询线索信息信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param clueInfo 实体
	 * @return List<ClueInfo>
	 */
	Map<String, Object> queryClueInfoForPage(Integer pagenum, Integer pagesize, String sort, ClueInfo clueInfo);
	 
	 /**
	 * @Title: queryClueInfoByProperty
	 * @Description:根据属性查询线索信息
	 * @return List<ClueInfo>
	 */
	 List<ClueInfo> queryClueInfoByProperty(Map<String, Object> map);	
	 
	 HSSFWorkbook exportByProperty(Map<String, Object> map);	
	 
}
