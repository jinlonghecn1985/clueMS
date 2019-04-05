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
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.ClueInfoService;
import com.hnjing.utils.ClassUtil;
import com.hnjing.utils.DateUtil;
import com.hnjing.utils.HttpTool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: ClueInfoController
 * @Description: 线索信息HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@RestController
@Api(description="线索信息")
public class ClueInfoController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private ClueInfoService clueInfoService;
	
	@Autowired
	private BMSService bmsService;

	

	
	
	@ApiOperation(value = "更新 根据线索信息标识更新线索信息信息", notes = "根据线索信息标识更新线索信息信息")
	@RequestMapping(value = "/clueinfo/{cId:.+}", method = RequestMethod.PUT)
	public Object modifyClueInfoById(HttpServletResponse response,
			@PathVariable Integer cId,
			@ApiParam(value = "clueInfo", required = true) @RequestBody ClueInfo clueInfo
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ClueInfo tempClueInfo = clueInfoService.queryClueInfoByCId(cId);
		clueInfo.setCId(cId);
		if(null == tempClueInfo){
			throw new NotFoundException("线索信息");
		}
		return clueInfoService.modifyClueInfo(clueInfo);
	}

	@ApiOperation(value = "删除 根据线索信息标识删除线索信息信息", notes = "根据线索信息标识删除线索信息信息")
	@RequestMapping(value = "/clueinfo/{cId:.+}", method = RequestMethod.DELETE)
	public Object dropClueInfoByCId(HttpServletResponse response, @PathVariable Integer cId) {
		ClueInfo clueInfo = clueInfoService.queryClueInfoByCId(cId);
		if(null == clueInfo){
			throw new NotFoundException("线索信息");
		}
		return clueInfoService.dropClueInfoByCId(cId);
	}
	
	@ApiOperation(value = "查询 根据线索信息标识查询线索信息信息", notes = "根据线索信息标识查询线索信息信息")
	@RequestMapping(value = "/clueinfo/{cId:.+}", method = RequestMethod.GET)
	public Object queryClueInfoById(HttpServletResponse response,
			@PathVariable Integer cId) {
		ClueInfo clueInfo = clueInfoService.queryClueInfoByCId(cId);
		if(null == clueInfo){
			throw new NotFoundException("线索信息");
		}
		return clueInfo;
	}
	
	@ApiOperation(value = "查询 根据线索信息属性查询线索信息信息列表", notes = "根据线索信息属性查询线索信息信息列表")
	@RequestMapping(value = "/clueinfo", method = RequestMethod.GET)
	public Object queryClueInfoList(HttpServletResponse response,
			ClueInfo clueInfo) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return clueInfoService.queryClueInfoByProperty(ClassUtil.transBean2Map(clueInfo, false));
	}
	
	@ApiOperation(value = "查询分页 根据线索信息属性分页查询线索信息信息列表", notes = "根据线索信息属性分页查询线索信息信息列表")
	@RequestMapping(value = "/clueinfos", method = RequestMethod.GET)
	public Object queryClueInfoPage(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, ClueInfo clueInfo) throws AuthorityException{			
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo()!=null && ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
			clueInfo.setCreatedNo(ui.getUserCode());
		}
		
		return clueInfoService.queryClueInfoForPage(pagenum, pagesize, sort, clueInfo);
	}
	
	@ApiOperation(value = "导出线索资料", notes = "根据线索信息属性分页查询线索信息信息列表")
	@RequestMapping(value = "/clueinfos/export", method = RequestMethod.GET)
	public void queryClueInfoPage(HttpServletResponse response, HttpServletRequest request, ClueInfo clueInfo) throws AuthorityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, IOException{			
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo()!=null && ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==1) {
			clueInfo.setCreatedNo(ui.getUserCode());
		}
		
		String fileName = "clue_"+ui.getUserInfo().getUcode()+DateUtil.getDate().replace("-", "")+".xls";
		HSSFWorkbook wb = clueInfoService.exportByProperty(ClassUtil.transBean2Map(clueInfo, false));
		HttpTool.setResponseHeader(response, fileName);
		OutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
	}

}
