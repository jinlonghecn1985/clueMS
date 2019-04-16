package com.hnjing.core.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.hnjing.core.controller.bo.BusinessBo;
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.BusinessInfoService;
import com.hnjing.core.service.ClueInfoService;
import com.hnjing.utils.ClassUtil;
import com.hnjing.utils.DateUtil;
import com.hnjing.utils.HttpTool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: BusinessInfoController
 * @Description: 商机信息HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月20日 14时16分
 */
@RestController
@Api(description="商机信息")
public class BusinessInfoController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private BusinessInfoService businessInfoService;
	
	@Autowired
	private ClueInfoService clueInfoService;
	
	@Autowired
	private BMSService bmsService;

	
	@ApiOperation(value = "新增 添加商机信息信息", notes = "添加商机信息信息")
	@RequestMapping(value = "/businessinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addBusinessInfo(HttpServletResponse response,
			@ApiParam(value = "businessInfo") @RequestBody BusinessInfo businessInfo) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		businessInfo.setBId(null);
		businessInfoService.addBusinessInfo(businessInfo);
		return businessInfo;
	}
	
	
	@ApiOperation(value = "更新 根据商机信息标识更新商机信息信息", notes = "根据商机信息标识更新商机信息信息")
	@RequestMapping(value = "/businessinfo/{bId:.+}", method = RequestMethod.PUT)
	public Object modifyBusinessInfoById(HttpServletResponse response,
			@PathVariable Integer bId,
			@ApiParam(value = "businessInfo", required = true) @RequestBody BusinessInfo businessInfo
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		BusinessInfo tempBusinessInfo = businessInfoService.queryBusinessInfoByBId(bId);
		businessInfo.setBId(bId);
		if(null == tempBusinessInfo){
			throw new NotFoundException("商机信息");
		}
		return businessInfoService.modifyBusinessInfo(businessInfo);
	}

	@ApiOperation(value = "删除 根据商机信息标识删除商机信息信息", notes = "根据商机信息标识删除商机信息信息")
	@RequestMapping(value = "/businessinfo/{bId:.+}", method = RequestMethod.DELETE)
	public Object dropBusinessInfoByBId(HttpServletResponse response, @PathVariable Integer bId) {
		BusinessInfo businessInfo = businessInfoService.queryBusinessInfoByBId(bId);
		if(null == businessInfo){
			throw new NotFoundException("商机信息");
		}
		return businessInfoService.dropBusinessInfoByBId(bId);
	}
	
	@ApiOperation(value = "查询 根据商机信息标识查询商机信息信息", notes = "根据商机信息标识查询商机信息信息")
	@RequestMapping(value = "/businessinfo/{bId:.+}", method = RequestMethod.GET)
	public Object queryBusinessInfoById(HttpServletResponse response, HttpServletRequest request,
			@PathVariable Integer bId, String sale) {
		BusinessInfo businessInfo = null;
		if(bId!=null && bId.intValue()==0 && sale!=null) {
			businessInfo = businessInfoService.queryBusinessInfoBySaleToken(sale);
			if(businessInfo!=null && businessInfo.getcPhone()!=null && businessInfo.getcPhone().length()>7) {
				businessInfo.setcPhone(businessInfo.getcPhone().substring(0, 3)+"****"+businessInfo.getcPhone().substring(businessInfo.getcPhone().length()-4));
			}else {
				return "index.html";
			}
		}else {
			UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
			if(ui.getUserInfo()==null) {
				throw new AuthorityException("用户权限错误");
			}
			businessInfo = businessInfoService.queryBusinessInfoByBId(bId);
		}		
		if(null == businessInfo){
			throw new NotFoundException("商机信息");
		}
		BusinessBo bb = new BusinessBo();
		bb.setBusinessInfo(businessInfo);
		ClueInfo ci = clueInfoService.queryClueInfoByCId(businessInfo.getClueId());
		if(bId.intValue()==0) {
			if(ci!=null && ci.getCPhone()!=null && ci.getCPhone().length()>7) {
				ci.setCPhone(ci.getCPhone().substring(0, 3)+"****"+ci.getCPhone().substring(ci.getCPhone().length()-4));
			}
		}
		bb.setClueInfo(ci);
		return bb;
	}
	
	@ApiOperation(value = "查询 根据商机信息属性查询商机信息信息列表", notes = "根据商机信息属性查询商机信息信息列表")
	@RequestMapping(value = "/businessinfo", method = RequestMethod.GET)
	public Object queryBusinessInfoList(HttpServletResponse response, HttpServletRequest request,
			BusinessInfo businessInfo, Integer notbId, String accurateCustomer, String accuratePhone) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		boolean hasParam = false;
		Map<String, Object> query = ClassUtil.transBean2Map(businessInfo, false);
		if(query==null) {
			query = new HashMap<String, Object>();
		}
		if(notbId!=null && notbId.intValue()>0) {
			query.put("notbId", notbId);
			BusinessInfo bi = businessInfoService.queryBusinessInfoByBId(notbId);
			if(null == bi){
				throw new NotFoundException("商机信息");
			}
			if(accurateCustomer!=null && accurateCustomer.trim().length()>0) {
				query.put("accurateCustomer", bi.getCCustomer()); hasParam = true;
			}
			if(accuratePhone!=null && accuratePhone.trim().length()>0) {
				query.put("accuratePhone", bi.getcPhone()); hasParam = true;
			}
			if(!hasParam) {
				throw new ParameterException("accurateCustomer, accuratePhone", "企业名称或联系电话至少传入一个。");
			}
		}else {
			query.put("accurateCustomer", "hejinlong@hnjing.com");
		}
		
		return businessInfoService.queryBusinessInfoByProperty(query);
	}
	
	@ApiOperation(value = "查询分页 根据商机信息属性分页查询商机信息信息列表", notes = "根据商机信息属性分页查询商机信息信息列表")
	@RequestMapping(value = "/businessinfos", method = RequestMethod.GET)
	public Object queryBusinessInfoPage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, BusinessInfo businessInfo)  throws AuthorityException{
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo()!=null && ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
			businessInfo.setGmtCreatedUser(ui.getUserCode());
		}
		return businessInfoService.queryBusinessInfoRepeatForPage(pagenum, pagesize, sort, businessInfo);
	}
	
	
	@ApiOperation(value = "导出商机资料", notes = "商机")
	@RequestMapping(value = "/businessinfos/export", method = RequestMethod.GET)
	public void queryClueInfoPage(HttpServletResponse response, HttpServletRequest request, BusinessInfo businessInfo) throws AuthorityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, IOException{			
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null || (ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1)) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo()!=null && ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==3) {
			businessInfo.setGmtCreatedUser(ui.getUserCode());
		}
		
		String fileName = "business_"+ui.getUserInfo().getUcode()+DateUtil.getDate().replace("-", "")+".xls";
		HSSFWorkbook wb = businessInfoService.exportByProperty(ClassUtil.transBean2Map(businessInfo, false));
		HttpTool.setResponseHeader(response, fileName);
		OutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}

}
