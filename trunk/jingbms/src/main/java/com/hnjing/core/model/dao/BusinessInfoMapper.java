package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.service.impl.bo.BusinessInfoBo;

/**
 * @ClassName: BusinessInfoMapper
 * @Description: 商机信息映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月20日 14时16分
 */
@Mapper
public interface BusinessInfoMapper {

	/**
	 * @Title: addBusinessInfo
	 * @Description:添加商机信息
	 * @param businessInfo 实体
	 * @return Integer
	 */
	Integer addBusinessInfo(BusinessInfo businessInfo);
	
	/**
	 * @Title modifyBusinessInfo
	 * @Description:修改商机信息
	 * @param businessInfo 实体
	 * @return Integer
	 */
	Integer modifyBusinessInfo(BusinessInfo businessInfo);
	
	/**
	 * @Title: dropBusinessInfoByBId
	 * @Description:删除商机信息
	 * @param bId 实体标识
	 * @return Integer
	 */
	Integer dropBusinessInfoByBId(Integer bId);
	
	/**
	 * @Title: queryBusinessInfoByBId
	 * @Description:根据实体标识查询商机信息
	 * @param bId 实体标识
	 * @return BusinessInfo
	 */
	BusinessInfo queryBusinessInfoByBId(Integer bId);
	 
	/**
	 * @Title: queryBusinessInfoForPage
	 * @Description: 根据商机信息属性与分页信息分页查询商机信息信息
	 * @param pageBounds 分页信息
	 * @param businessInfo 实体
	 * @return List<BusinessInfo>
	 */
	List<BusinessInfo> queryBusinessInfoForPage(PageBounds pageBounds, @Param("businessInfo") BusinessInfo businessInfo);
	
	
	List<BusinessInfoBo> queryBusinessInfoBoByProperty(@Param("businessInfo") Map<String, Object> map);
	 
	 /**
	  * @Title: queryBusinessInfoByProperty
	  * @Description:根据属性查询商机信息
	  * @return List<BusinessInfo>
	  */
	List<BusinessInfo> queryBusinessInfoByProperty(@Param("businessInfo") Map<String, Object> map);
	 
	Map<String, Object> queryBusinessRepeatCount(@Param("bId")Integer bId, @Param("cCustomer")String cCustomer, @Param("cPhone") String cPhone);
	 
}
