/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BMSController.java
 * @Prject: BMS
 * @Package: com.hnjing.core.controller
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年3月20日 下午2:39:49
 * @version: V1.0  
 */
package com.hnjing.core.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hnjing.config.web.exception.AuthorityException;
import com.hnjing.config.web.exception.NotFoundException;
import com.hnjing.config.web.exception.ParameterException;
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.model.entity.Employee;
import com.hnjing.core.model.entity.Reply;
import com.hnjing.core.model.entity.UserInfo;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.BusinessInfoService;
import com.hnjing.core.service.ClueInfoService;
import com.hnjing.core.service.EmployeeService;
import com.hnjing.core.service.UserInfoService;
import com.hnjing.utils.file.office.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: BMSController
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2019年3月20日 下午2:39:49
 */
@RestController
@Api(description="商机信息管理核心")
public class BMSController {
	@Value("${file.upload.path}")
	private String filePath;
	private static final Logger logger = LoggerFactory.getLogger(BMSController.class);
	@Autowired
	private ClueInfoService clueInfoService;
	
	@Autowired
	private BusinessInfoService businessInfoService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private BMSService bmsService;
	
	@ApiOperation(value = "新增 添加线索信息信息", notes = "添加线索信息信息")
	@RequestMapping(value = "/clueinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addClueInfo(HttpServletResponse response, HttpServletRequest request,
			@ApiParam(value = "clueInfo") @RequestBody ClueInfo clueInfo)  throws AuthorityException {		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		clueInfo.setCId(null);
		clueInfo.setCreatedNo(ui.getUserName());
		bmsService.saveClueInfo(clueInfo);
		return clueInfo;
	}
	
	@ApiOperation(value = "线索核查", notes = "线索核查")
	@RequestMapping(value = "/bmsclueinfo/{cId:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object checkClueInfo(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer cId, @ApiParam(value = "clueInfo") @RequestBody ClueInfo clueInfo)  throws AuthorityException {		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		
		ClueInfo tempClueInfo = clueInfoService.queryClueInfoByCId(cId);
		if(null == tempClueInfo){
			throw new NotFoundException("线索信息");
		}
		clueInfo.setCPhone(tempClueInfo.getCPhone());
		clueInfo.setCId(cId);
		clueInfo.setModifyNo(ui.getUserName());
		bmsService.saveClueInfo2(clueInfo);
		return clueInfo;
	}
	
	@ApiOperation(value = "用户授权码查询", notes = "用户授权码查询")
	@RequestMapping(value = "/token/query/{token:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object checkToken(HttpServletResponse response, HttpServletRequest request, @PathVariable String token) {
		if(token==null || token.trim().length()!=32) {
			throw new ParameterException("token", "用户授权码没有传入或长度不是32位");
		}
		UserInfo ui = userInfoService.queryUserInfoByToken(token);
		if(ui==null || ui.getUstatus().intValue()==1) {
			throw new ParameterException("user", "错误授权码或用户已失效");
		}
		UserInfo u = new UserInfo();
		u.setUcode(ui.getUcode());
		u.setUname(ui.getUname());
		u.setToken(ui.getToken());
		u.setUlevel(ui.getUlevel());
		return u;
	}
	
	@ApiOperation(value = "用户授权码刷新", notes = "用户授权码刷新")
	@RequestMapping(value = "/token/refresh/{ucode:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object refreshToken(HttpServletResponse response, HttpServletRequest request, @PathVariable String ucode) throws AuthorityException{
		if(ucode==null || ucode.trim().length()==0) {
			throw new ParameterException("ucode", "用户编号必传！");
		}		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		//自身授权码刷新
		if(ui.getUserInfo().getUcode().equals(ucode)) {
			return bmsService.refreshToken(ui.getUserInfo());
		}
		//管理员
		if(ui.getUserInfo().getUlevel().intValue()==9) {
			UserInfo temp = userInfoService.queryUserInfoByUcode(ucode);
			if(temp==null || temp.getUstatus().intValue()==1) {
				throw new ParameterException("user", "用户已失效");
			}
			return bmsService.refreshToken(temp);
		}
		throw new AuthorityException("您的权限不够！");
	}
	
	@ApiOperation(value = "转发商机信息信息", notes = "商机信息信息")
	@RequestMapping(value = "/bmsBusiness/{bId:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object processBusinessInfo(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer bId, @ApiParam(value = "businessInfo") @RequestBody BusinessInfo businessInfo) 
					throws AuthorityException {		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
			throw new AuthorityException("用户权限不够");
		}
		businessInfo.setGmtModifyUser(ui.getUserName());
		
		
		BusinessInfo tempBusinessInfo = businessInfoService.queryBusinessInfoByBId(bId);		
		if(null == tempBusinessInfo){
			throw new NotFoundException("商机信息");
		}
		if(tempBusinessInfo.getSjStatus().intValue()!=0) {
			throw new ParameterException("businessinfo", "商机状态已变更，无法执行此操作。");
		}
		if(businessInfo.getEmployee()==null) {
			throw new ParameterException("Employee", "受理人员参数为空。");
		}
		businessInfo.setCCustomer(tempBusinessInfo.getCCustomer());
		businessInfo.setClueId(tempBusinessInfo.getClueId()); //防止串
		businessInfo.setGmtCreatedUser(null);
		businessInfo.setcGoods(tempBusinessInfo.getcGoods());
		
		Employee e = null;
		if(businessInfo.getEmployee()!=null) {
			e = employeeService.queryEmployeeByPId(Integer.parseInt(businessInfo.getEmployee()));
			if(e==null) {
				throw new ParameterException("Employee", "无效商机受理人。");
			}
			businessInfo.setDepartment(e.getPersonDep());
			businessInfo.setEmployee(e.getPersonName());
		}		
		return bmsService.saveBusiness(businessInfo, e);
	}
	
	
	@ApiOperation(value = "完成商机信息信息", notes = "完成商机信息信息")
	@RequestMapping(value = "/bmsDealBusiness/{bId:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object dealBusinessInfo(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer bId, @ApiParam(value = "businessInfo") @RequestBody BusinessInfo businessInfo, String sale) 
					throws AuthorityException {		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));		
		BusinessInfo tempBusinessInfo = businessInfoService.queryBusinessInfoByBId(bId);	
		if(null == tempBusinessInfo){
			throw new NotFoundException("商机信息");
		}		
		if(sale==null || !sale.equals(tempBusinessInfo.getSaletoken())) {
			if(ui.getUserInfo()==null) {
				throw new AuthorityException("用户权限错误");
			}
			if(ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
				throw new AuthorityException("用户权限不够");
			}	
		}	
		
		if(tempBusinessInfo.getSjStatus().intValue()!=1) {
			throw new ParameterException("businessinfo", "商机状态已变更，无法执行此操作。");
		}
		
		BusinessInfo businessInfo2 = new BusinessInfo();
		businessInfo2.setClueId(tempBusinessInfo.getClueId());
		businessInfo2.setBId(tempBusinessInfo.getBId());
		businessInfo2.setGmtModifyUser((ui==null||ui.getUserInfo()==null)?tempBusinessInfo.getEmployee():ui.getUserName());
		businessInfo2.setBnote(businessInfo.getBnote());
		businessInfo2.setBusiness(businessInfo.getBusiness());
		businessInfo2.setSjStatus(8);
		businessInfo2.setSaletoken("hejinlong01");
		return bmsService.dealBusiness(businessInfo2);
	}
	
	@ApiOperation(value = "关闭商机信息信息", notes = "关闭商机信息信息")
	@RequestMapping(value = "/bmsBusiness/{bId:.+}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
	public Object closeBusinessInfo(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer bId, @RequestParam(value = "bNote", required = true) String bNote, String sale) 
					throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if(bNote==null || bNote.length()<2) {
			throw new ParameterException("bNote", "商机关闭原因必传，且长度至少2位！");
		}		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		BusinessInfo tempBusinessInfo = businessInfoService.queryBusinessInfoByBId(bId);	
		if(null == tempBusinessInfo){
			throw new NotFoundException("商机信息");
		}		
		if(sale==null || !sale.equals(tempBusinessInfo.getSaletoken())) {
			if(ui.getUserInfo()==null) {
				throw new AuthorityException("用户权限错误");
			}
			if(ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
				throw new AuthorityException("用户权限不够");
			}	
		}	
		if(tempBusinessInfo.getSjStatus().intValue()==7 || tempBusinessInfo.getSjStatus().intValue()==8) {
			throw new ParameterException("businessinfo", "商机状态已变更，无法执行此操作。");
		}	
		BusinessInfo businessInfo = new BusinessInfo();
		businessInfo.setBId(bId);
		businessInfo.setClueId(tempBusinessInfo.getClueId());
		businessInfo.setBnote(bNote);
		businessInfo.setGmtModifyUser((ui==null||ui.getUserInfo()==null)?tempBusinessInfo.getEmployee():ui.getUserName());	
		businessInfo.setSaletoken("hejinlong01");
		return bmsService.closeBusiness(businessInfo);
	}
	
	@ApiOperation(value = "回复商机沟通信息", notes = "回复商机沟通信息")
	@RequestMapping(value = "/bmsReply/{bId:.+}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addReplyInfo(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer bId, @ApiParam(value = "reply") @RequestBody Reply reply, String sale) 
					throws AuthorityException {
		if(reply==null || reply.getContent()==null) {
			throw new ParameterException("content", "销售反馈必须填写");
		}
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		BusinessInfo tempBusinessInfo = businessInfoService.queryBusinessInfoByBId(bId);	
		if(null == tempBusinessInfo){
			throw new NotFoundException("商机信息");
		}
		if(sale!=null && sale.equals(tempBusinessInfo.getSaletoken())) {
			reply.setCreatedMan(tempBusinessInfo.getEmployee());	
			reply.setEmployee(tempBusinessInfo.getGmtModifyUser());
		}else {
			if(ui.getUserInfo()!=null) {
				if(ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
					throw new AuthorityException("用户权限不够");
				}
				
				reply.setCreatedMan(ui.getUserName());	
				reply.setEmployee(tempBusinessInfo.getGmtModifyUser());
			}else {
				throw new AuthorityException("用户权限错误");
			}			
		}	
		
		if(tempBusinessInfo.getSjStatus().intValue()!=1) {
			throw new ParameterException("businessinfo", "商机状态已变更，无法执行此操作。");
		}	
		reply.setBId(bId);
		return bmsService.followBusiness(reply);
	}
	
	
	
	@ApiOperation(value = "上传轩辕线索信息", notes = "上传轩辕线索信息")
	@RequestMapping(value = "/bmsxuanyuan/upload", method = RequestMethod.POST)
	public Object uploadXuanYuanClue( HttpServletRequest request, @RequestParam("xlsFile") MultipartFile xlsFile) {
		if (xlsFile.isEmpty()) {
			throw new ParameterException("file", "请选择要上传的文件！");
		}
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		
		String fileName = xlsFile.getOriginalFilename().toLowerCase();
		logger.info("上传文件成功" + fileName);
		File dest = new File(filePath + fileName);
		try {
			xlsFile.transferTo(dest);
			logger.info("保存成功" + filePath + fileName);
		} catch (IOException e) {
			logger.error(e.toString(), e);
			throw new ParameterException("file", "保存文件失败");
		}
		if (fileName != null && (fileName.endsWith("xlsx") || fileName.endsWith("xls"))) {
			List<List<String>> xlsData;
			try {
				xlsData = ExcelUtil.readDocFile(filePath + fileName, true);
			} catch (Exception e) {
				throw new ParameterException("file", "无法解析excel文档");
			}
			if (xlsData != null && xlsData.size() > 0) {
				bmsService.uploadXYClue(ui.getUserName(), xlsData);
			}
			return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body onload='JavaScript:history.go(-1)'></body></html>";

		}
		throw new ParameterException("file", "错误的文件格式，当前仅允许上传xls或xlsx表格！");
	}
	
	
	@ApiOperation(value = "上传ejing线索信息", notes = "上传ejing线索信息")
	@RequestMapping(value = "/bmsejing/upload", method = RequestMethod.POST)
	public Object uploadEjingClueBusiness( HttpServletRequest request, @RequestParam("xlsFile") MultipartFile xlsFile) {
		if (xlsFile.isEmpty()) {
			throw new ParameterException("file", "请选择要上传的文件！");
		}
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		
		String fileName = xlsFile.getOriginalFilename().toLowerCase();
		logger.info("上传文件成功" + fileName);
		File dest = new File(filePath + fileName);
		try {
			xlsFile.transferTo(dest);
			logger.info("保存成功" + filePath + fileName);
		} catch (IOException e) {
			logger.error(e.toString(), e);
			throw new ParameterException("file", "保存文件失败");
		}
		if (fileName != null && (fileName.endsWith("xlsx") || fileName.endsWith("xls"))) {
			List<List<String>> xlsData;
			try {
				xlsData = ExcelUtil.readDocFile(filePath + fileName, true);
			} catch (Exception e) {
				throw new ParameterException("file", "无法解析excel文档");
			}
			if (xlsData != null && xlsData.size() > 0) {
				bmsService.uploadEjingClue(ui.getUserName(), xlsData);
			}
			return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body onload='JavaScript:history.go(-1)'></body></html>";

		}
		throw new ParameterException("file", "错误的文件格式，当前仅允许上传xls或xlsx表格！");
	}
}
