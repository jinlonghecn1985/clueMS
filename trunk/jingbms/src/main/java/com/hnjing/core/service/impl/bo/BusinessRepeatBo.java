/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BusinessRepeatBo.java
 * @Prject: BMS
 * @Package: com.hnjing.core.service.impl.bo
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年4月12日 上午11:42:40
 * @version: V1.0  
 */
package com.hnjing.core.service.impl.bo;

import java.util.List;

import com.hnjing.core.model.entity.BusinessInfo;

/**
 * @ClassName: BusinessRepeatBo
 * @Description: 
 * @author: Jinlong He
 * @date: 2019年4月12日 上午11:42:40
 */
public class BusinessRepeatBo {
	
	private BusinessInfo businessInfo;
	
	private Integer repeatNameCount;
	
	private Integer repeatPhoneCount;
	
	private List<BusinessInfo> clueInfoList;

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
	 * @return the repeatNameCount
	 */
	public Integer getRepeatNameCount() {
		return repeatNameCount;
	}

	/**
	 * @param repeatNameCount the repeatNameCount to set
	 */
	public void setRepeatNameCount(Integer repeatNameCount) {
		this.repeatNameCount = repeatNameCount;
	}

	/**
	 * @return the repeatPhoneCount
	 */
	public Integer getRepeatPhoneCount() {
		return repeatPhoneCount;
	}

	/**
	 * @param repeatPhoneCount the repeatPhoneCount to set
	 */
	public void setRepeatPhoneCount(Integer repeatPhoneCount) {
		this.repeatPhoneCount = repeatPhoneCount;
	}

	/**
	 * @return the clueInfoList
	 */
	public List<BusinessInfo> getClueInfoList() {
		return clueInfoList;
	}

	/**
	 * @param clueInfoList the clueInfoList to set
	 */
	public void setClueInfoList(List<BusinessInfo> clueInfoList) {
		this.clueInfoList = clueInfoList;
	}

}
