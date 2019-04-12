/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: ClueInfoBo.java
 * @Prject: BMS
 * @Package: com.hnjing.core.service.impl.bo
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年4月11日 下午5:31:40
 * @version: V1.0  
 */
package com.hnjing.core.service.impl.bo;

import java.util.List;

import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;

/**
 * @ClassName: ClueInfoBo
 * @Description: 
 * @author: Jinlong He
 * @date: 2019年4月11日 下午5:31:40
 */
public class ClueRepeatBo  extends BusinessInfo{
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: 
	 */
	private static final long serialVersionUID = -2724693161273298795L;

	private ClueInfo clueInfo;
	
	private Integer repeatNameCount;
	
	private Integer repeatPhoneCount;
	
	private List<ClueInfo> clueInfoList;

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
	public List<ClueInfo> getClueInfoList() {
		return clueInfoList;
	}

	/**
	 * @param clueInfoList the clueInfoList to set
	 */
	public void setClueInfoList(List<ClueInfo> clueInfoList) {
		this.clueInfoList = clueInfoList;
	}

}
