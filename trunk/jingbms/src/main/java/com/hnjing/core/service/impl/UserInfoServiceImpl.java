package com.hnjing.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnjing.utils.Constant;
import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.utils.paginator.domain.PageList;
import com.hnjing.utils.paginator.domain.PageService;
import java.util.UUID;


import com.hnjing.core.model.entity.UserInfo;
import com.hnjing.core.model.dao.UserInfoMapper;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.UserInfoService;

/**
 * @ClassName: UserInfo
 * @Description: 用户信息表服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月26日 10时19分
 */
@Service("userInfoService")
@Transactional(readOnly=true)
public class  UserInfoServiceImpl implements UserInfoService {	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
    private UserInfoMapper userInfoMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	@Autowired
	private BMSService bmsService;
	
	/**
	 * @Title: addUserInfo
	 * @Description:添加用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public UserInfo addUserInfo(UserInfo userInfo){
		userInfo.setToken(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		int ret = userInfoMapper.addUserInfo(userInfo);
		if(ret>0){
			bmsService.refreshToken(userInfo);
			return userInfo;
		}
		return null;
	}
	
	/**
	 * @Title modifyUserInfo
	 * @Description:修改用户信息表
	 * @param userInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyUserInfo(UserInfo userInfo){
		return userInfoMapper.modifyUserInfo(userInfo);
	}
	
	/**
	 * @Title: dropUserInfoByUcode
	 * @Description:删除用户信息表
	 * @param ucode 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropUserInfoByUcode(String ucode){
		return userInfoMapper.dropUserInfoByUcode(ucode);
	}
	
	/**
	 * @Title: queryUserInfoByUcode
	 * @Description:根据实体标识查询用户信息表
	 * @param ucode 实体标识
	 * @return UserInfo
	 */
	@Override
	public UserInfo queryUserInfoByUcode(String ucode){
		return userInfoMapper.queryUserInfoByUcode(ucode);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryUserInfoForPage
	 * @Description: 根据用户信息表属性与分页信息分页查询用户信息表信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param userInfo 实体
	 * @return List<UserInfo>
	 */
	@Override
	public Map<String, Object> queryUserInfoForPage(Integer pagenum, Integer pagesize, String sort, UserInfo userInfo){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, UserInfo.class);
		List<UserInfo> entityList = userInfoMapper.queryUserInfoForPage(pageBounds, userInfo);
		if(entityList!=null && entityList.size()>0) {
			for(UserInfo ui : entityList) {
				ui.setToken(null);
			}
		}
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, UserInfo.class);
		}
		
		PageList<UserInfo> pagelist = (PageList<UserInfo>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryUserInfoByProperty
	 * @Description:根据属性查询用户信息表
	 * @return List<UserInfo>
	 */
	@Override
	public List<UserInfo> queryUserInfoByProperty(Map<String, Object> map){
		return userInfoMapper.queryUserInfoByProperty(map);
	}

	/*
	 * @Title: queryUserInfoByToken
	 * @Description: TODO
	 * @param @param token
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param token
	 * @return
	 * @see com.hnjing.core.service.UserInfoService#queryUserInfoByToken(java.lang.String)
	 */ 
	@Override
	public UserInfo queryUserInfoByToken(String token) {		
		return userInfoMapper.queryUserInfoByToken(token);
	}

	/*
	 * @Title: modifyUserToken
	 * @Description: TODO
	 * @param @param ucode
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param ucode
	 * @return
	 * @see com.hnjing.core.service.UserInfoService#modifyUserToken(java.lang.String)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public UserInfo modifyUserToken(String ucode) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUcode(ucode);
		userInfo.setToken(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		userInfoMapper.modifyUserInfo(userInfo);
		return userInfo;
	}


}
