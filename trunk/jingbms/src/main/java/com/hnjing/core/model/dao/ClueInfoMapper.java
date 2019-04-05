package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.ClueInfo;

/**
 * @ClassName: ClueInfoMapper
 * @Description: 线索信息映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Mapper
public interface ClueInfoMapper {

	/**
	 * @Title: addClueInfo
	 * @Description:添加线索信息
	 * @param clueInfo 实体
	 * @return Integer
	 */
	Integer addClueInfo(ClueInfo clueInfo);
	
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
	 * @param pageBounds 分页信息
	 * @param clueInfo 实体
	 * @return List<ClueInfo>
	 */
	List<ClueInfo> queryClueInfoForPage(PageBounds pageBounds, @Param("clueInfo") ClueInfo clueInfo);
	 
	 /**
	  * @Title: queryClueInfoByProperty
	  * @Description:根据属性查询线索信息
	  * @return List<ClueInfo>
	  */
	 List<ClueInfo> queryClueInfoByProperty(@Param("clueInfo") Map<String, Object> map);
	 
	 
	 
}
