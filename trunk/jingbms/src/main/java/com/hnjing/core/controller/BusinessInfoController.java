package com.hnjing.core.controller;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

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
import com.hnjing.core.controller.bo.BusinessBo;
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.BusinessInfo;
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
	public Object queryBusinessInfoById(HttpServletResponse response,
			@PathVariable Integer bId) {
		BusinessInfo businessInfo = businessInfoService.queryBusinessInfoByBId(bId);
		if(null == businessInfo){
			throw new NotFoundException("商机信息");
		}
		BusinessBo bb = new BusinessBo();
		bb.setBusinessInfo(businessInfo);
		bb.setClueInfo(clueInfoService.queryClueInfoByCId(businessInfo.getClueId()));
		return bb;
	}
	
	@ApiOperation(value = "查询 根据商机信息属性查询商机信息信息列表", notes = "根据商机信息属性查询商机信息信息列表")
	@RequestMapping(value = "/businessinfo", method = RequestMethod.GET)
	public Object queryBusinessInfoList(HttpServletResponse response,
			BusinessInfo businessInfo) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return businessInfoService.queryBusinessInfoByProperty(ClassUtil.transBean2Map(businessInfo, false));
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
		return businessInfoService.queryBusinessInfoForPage(pagenum, pagesize, sort, businessInfo);
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
