/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BMSService.java
 * @Prject: BMS
 * @Package: com.hnjing.core.service
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年3月26日 上午9:48:35
 * @version: V1.0  
 */
package com.hnjing.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.model.entity.Employee;
import com.hnjing.core.model.entity.Reply;
import com.hnjing.core.model.entity.UserInfo;

/**
 * @ClassName: BMSService
 * @Description: 商机管理接口
 * @author: Jinlong He
 * @date: 2019年3月26日 上午9:48:35
 */
public interface BMSService {
	
	UserInfo getUserInfo(HttpServletRequest request);
	
	/** 
	* @Title: saveClueInfo 
	* @Description: 添加线索
	* @param clue
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object saveClueInfo(ClueInfo clue);	
	
	/** 
	* @Title: saveBusiness 
	* @Description: 商机转发 
	* @param business
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object saveBusiness(BusinessInfo business, Employee employee);
	
	/**
	* @Title: closeBusiness 
	* @Description: 商机关闭
	* @param business
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object closeBusiness(BusinessInfo business);
	
	/** 
	* @Title: dealClueInfo 
	* @Description: 完成商机
	* @param clue
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object dealBusiness(BusinessInfo business);	
	
	/** 
	* @Title: followBusiness 
	* @Description: 商机回复
	* @param reply
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object followBusiness(Reply reply);

	/** 
	* @Title: refreshToken 
	* @Description: 用户授权码刷新
	* @param userInfo
	* @return  
	* Object    返回类型 
	* @throws 
	*/
	Object refreshToken(UserInfo userInfo);

	/** 
	* @Title: saveClueInfo2 
	* @Description: 
	* @param clueInfo  
	* void    返回类型 
	* @throws 
	*/
	ClueInfo saveClueInfo2(ClueInfo clueInfo);

	/** 
	* @Title: uploadXYClue 
	* @Description: 上传轩辕线索
	* @param userName
	* @param xlsData  
	* void    返回类型 
	* @throws 
	*/
	Object uploadXYClue(String userName, List<List<String>> xlsData);

	/** 
	* @Title: uploadEjingClue 
	* @Description: 上传Ejing线索与商机-
	* @param userName
	* @param xlsData  
	* void    返回类型 
	* @throws 
	*/
	Object uploadEjingClue(String userName, List<List<String>> xlsData);

}
