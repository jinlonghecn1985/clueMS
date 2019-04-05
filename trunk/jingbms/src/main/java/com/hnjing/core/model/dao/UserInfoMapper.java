package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.UserInfo;

/**
 * @ClassName: UserInfoMapper
 * @Description: 用户信息表映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月26日 10时19分
 */
@Mapper
public interface UserInfoMapper {

	/**
	 * @Title: addUserInfo
	 * @Description:添加用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	Integer addUserInfo(UserInfo userInfo);
	
	/**
	 * @Title modifyUserInfo
	 * @Description:修改用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	Integer modifyUserInfo(UserInfo userInfo);
	
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
	 * @param pageBounds 分页信息
	 * @param userInfo 实体
	 * @return List<UserInfo>
	 */
	List<UserInfo> queryUserInfoForPage(PageBounds pageBounds, @Param("userInfo") UserInfo userInfo);
	 
	 /**
	  * @Title: queryUserInfoByProperty
	  * @Description:根据属性查询用户信息表
	  * @return List<UserInfo>
	  */
	 List<UserInfo> queryUserInfoByProperty(@Param("userInfo") Map<String, Object> map);
	 
	 
	 
}
