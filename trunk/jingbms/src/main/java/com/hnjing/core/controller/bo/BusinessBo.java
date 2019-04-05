/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BusinessBo.java
 * @Prject: BMS
 * @Package: com.hnjing.core.controller
 * @Description: 
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年3月22日 下午5:23:13
 * @version: V1.0  
 */
package com.hnjing.core.controller.bo;

import java.io.Serializable;

import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;

/**
 * @ClassName: BusinessBo
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2019年3月22日 下午5:23:13
 */
public class BusinessBo implements Serializable{
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: 
	 */
	private static final long serialVersionUID = 1L;
	private BusinessInfo businessInfo;
	private ClueInfo clueInfo;
	/**
	 * @return the businessInfo
	 */
	public BusinessInfo getBusinessInfo() {
		return businessInfo;
	}
	/**
	 * @param businessInfo the businessInfo to set
	 */
	public void setBusinessInfo(BusinessInfo businessInfo) {
		this.businessInfo = businessInfo;
	}
	/**
	 * @return the clueInfo
	 */
	public ClueInfo getClueInfo() {
		return clueInfo;
	}
	/**
	 * @param clueInfo the clueInfo to set
	 */
	public void setClueInfo(ClueInfo clueInfo) {
		this.clueInfo = clueInfo;
	}

}
