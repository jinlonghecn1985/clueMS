package com.hnjing.core.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.service.impl.bo.BusinessInfoBo;

/**
 * @ClassName: BusinessInfo
 * @Description: 商机信息服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月20日 14时16分
 */
public interface BusinessInfoService {

    /**
	 * @Title: addBusinessInfo
	 * @Description:添加商机信息
	 * @param businessInfo 实体
	 * @return Integer
	 */
	BusinessInfo addBusinessInfo(BusinessInfo businessInfo);
	
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
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param businessInfo 实体
	 * @return List<BusinessInfo>
	 */
	Map<String, Object> queryBusinessInfoForPage(Integer pagenum, Integer pagesize, String sort, BusinessInfo businessInfo);
	
	Map<String, Object> queryBusinessInfoRepeatForPage(Integer pagenum, Integer pagesize, String sort, BusinessInfo businessInfo);
	 
	
	 /**
	 * @Title: queryBusinessInfoByProperty
	 * @Description:根据属性查询商机信息
	 * @return List<BusinessInfo>
	 */
	 List<BusinessInfo> queryBusinessInfoByProperty(Map<String, Object> map);	
	 
	 List<BusinessInfoBo> queryBusinessInfoBoByProperty(Map<String, Object> map);
	 
	 HSSFWorkbook exportByProperty(Map<String, Object> map);

	/** 
	* @Title: queryBusinessInfoBySaleToken 
	* @Description: 根据代码查询商机信息 
	* @param saleToken
	* @return  
	* BusinessInfo    返回类型 
	* @throws 
	*/
	BusinessInfo queryBusinessInfoBySaleToken(String saleToken);
	 
}
