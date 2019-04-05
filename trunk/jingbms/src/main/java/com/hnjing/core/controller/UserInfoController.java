package com.hnjing.core.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hnjing.config.validation.BeanValidator;
import com.hnjing.config.web.exception.AuthorityException;
import com.hnjing.config.web.exception.NotFoundException;
import com.hnjing.config.web.exception.ParameterException;
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.UserInfo;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.UserInfoService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: UserInfoController
 * @Description: 用户信息表HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月26日 10时19分
 */
@RestController
@Api(description="用户信息表")
public class UserInfoController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private BMSService bmsService;

	
	@ApiOperation(value = "新增 添加用户信息表信息", notes = "添加用户信息表信息")
	@RequestMapping(value = "/userinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addUserInfo(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "userInfo") @RequestBody UserInfo userInfo) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("请先登录系统");
		}
		if(ui.getUserInfo()==null || ui.getUserInfo().getUlevel()==null || ui.getUserInfo().getUlevel().intValue()!=9) {			
			throw new AuthorityException("用户权限错误");
		}
		userInfo.setCreatedNo(ui.getUserName());
		userInfo.setUcode(userInfo.getUcode().trim());	
		userInfo.setToken(null);
		if(userInfo.getUcode()==null || userInfo.getUcode().length()<4) {
			throw new ParameterException("ucode", "用户工号必填且长度至少4位。");
		}		
		UserInfo tempUserInfo = userInfoService.queryUserInfoByUcode(userInfo.getUcode());
		if(tempUserInfo!=null) {
			throw new ParameterException("ucode", "用户工号重复，请核查。");
		}
		userInfoService.addUserInfo(userInfo);
		return userInfo;
	}
	
	
	@ApiOperation(value = "更新 根据用户信息表标识更新用户信息表信息", notes = "token值为20190326时刷新用户授权码")
	@RequestMapping(value = "/userinfo/{ucode:.+}", method = RequestMethod.PUT)
	public Object modifyUserInfoById(HttpServletResponse response, HttpServletRequest request,
			@PathVariable String ucode,
			@ApiParam(value = "userInfo", required = true) @RequestBody UserInfo userInfo
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("请先登录系统");
		}
		if(ui.getUserInfo()==null || ui.getUserInfo().getUlevel()==null || ui.getUserInfo().getUlevel().intValue()!=9) {			
			throw new AuthorityException("用户权限错误");
		}
		UserInfo tempUserInfo = userInfoService.queryUserInfoByUcode(ucode);
		userInfo.setUcode(ucode);
		if(null == tempUserInfo){
			throw new NotFoundException("用户信息表");
		}
		userInfo.setToken(null);
		userInfo.setModifyNo(ui.getUserName());
		return userInfoService.modifyUserInfo(userInfo);
	}
	
	

	@ApiOperation(value = "删除 根据用户信息表标识删除用户信息表信息", notes = "根据用户信息表标识删除用户信息表信息")
	@RequestMapping(value = "/userinfo/{ucode:.+}", method = RequestMethod.DELETE)
	public Object dropUserInfoByUcode(HttpServletResponse response, @PathVariable String ucode) {
		UserInfo userInfo = userInfoService.queryUserInfoByUcode(ucode);
		if(null == userInfo){
			throw new NotFoundException("用户信息表");
		}
		return userInfoService.dropUserInfoByUcode(ucode);
	}
	
	@ApiOperation(value = "查询 根据用户信息表标识查询用户信息表信息", notes = "根据用户信息表标识查询用户信息表信息")
	@RequestMapping(value = "/userinfo/{ucode:.+}", method = RequestMethod.GET)
	public Object queryUserInfoById(HttpServletResponse response,
			@PathVariable String ucode) {
		UserInfo userInfo = userInfoService.queryUserInfoByUcode(ucode);
		if(null == userInfo){
			throw new NotFoundException("用户信息表");
		}
		return userInfo;
	}
	
	@ApiOperation(value = "查询 根据用户信息表属性查询用户信息表信息列表", notes = "根据用户信息表属性查询用户信息表信息列表")
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public Object queryUserInfoList(HttpServletResponse response,
			UserInfo userInfo) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return userInfoService.queryUserInfoByProperty(ClassUtil.transBean2Map(userInfo, false));
	}
	
	@ApiOperation(value = "查询分页 根据用户信息表属性分页查询用户信息表信息列表", notes = "根据用户信息表属性分页查询用户信息表信息列表")
	@RequestMapping(value = "/userinfos", method = RequestMethod.GET)
	public Object queryUserInfoPage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, UserInfo userInfo) {				
		return userInfoService.queryUserInfoForPage(pagenum, pagesize, sort, userInfo);
	}

}
