package com.hnjing.core.service;

import java.util.List;
import java.util.Map;


import com.hnjing.core.model.entity.UserInfo;

/**
 * @ClassName: UserInfo
 * @Description: 用户信息表服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月26日 10时19分
 */
public interface UserInfoService {

    /**
	 * @Title: addUserInfo
	 * @Description:添加用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	UserInfo addUserInfo(UserInfo userInfo);
	
	/**
	 * @Title modifyUserInfo
	 * @Description:修改用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	Integer modifyUserInfo(UserInfo userInfo);
	
	UserInfo modifyUserToken(String ucode);
	
	/**
	 * @Title: dropUserInfoByUcode
	 * @Description:删除用户信息表
	 * @param ucode 实体标识
	 * @return Integer
	 */
	Integer dropUserInfoByUcode(String ucode);
	
	/**
	 * @Title: queryUserInfoByUcode
	 * @Description:根据实体标识查询用户信息表
	 * @param ucode 实体标识
	 * @return UserInfo
	 */
	UserInfo queryUserInfoByUcode(String ucode);
	
	UserInfo queryUserInfoByToken(String token);
	 
	/**
	 * @Title: queryUserInfoForPage
	 * @Description: 根据用户信息表属性与分页信息分页查询用户信息表信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param userInfo 实体
	 * @return List<UserInfo>
	 */
	Map<String, Object> queryUserInfoForPage(Integer pagenum, Integer pagesize, String sort, UserInfo userInfo);
	 
	 /**
	 * @Title: queryUserInfoByProperty
	 * @Description:根据属性查询用户信息表
	 * @return List<UserInfo>
	 */
	 List<UserInfo> queryUserInfoByProperty(Map<String, Object> map);	 
	 
}
