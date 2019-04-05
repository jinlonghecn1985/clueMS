/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: UserInfoBo.java
 * @Prject: BMS
 * @Package: com.hnjing.core.controller.bo
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年3月27日 下午3:54:16
 * @version: V1.0  
 */
package com.hnjing.core.controller.bo;

import com.hnjing.core.model.entity.UserInfo;

/**
 * @ClassName: UserInfoBo
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2019年3月27日 下午3:54:16
 */
public class UserInfoBo {
	
	private UserInfo userInfo;
	

	public UserInfoBo(UserInfo userInfo) {
		super();
		this.setUserInfo(userInfo);
	}
	public String getUserCode() {
		return userInfo.getUcode()+",";
	}
	
	public String getUserName() {
		return userInfo.getUcode()+","+userInfo.getUname();
	}
	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}
	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
