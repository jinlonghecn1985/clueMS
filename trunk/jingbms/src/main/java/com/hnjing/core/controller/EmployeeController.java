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
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.Employee;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.EmployeeService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: EmployeeController
 * @Description: 商机候选人HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@RestController
@Api(description="商机候选人")
public class EmployeeController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private BMSService bmsService;

	
	@ApiOperation(value = "新增 添加商机候选人信息", notes = "添加商机候选人信息")
	@RequestMapping(value = "/employee", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addEmployee(HttpServletResponse response,
			@ApiParam(value = "employee") @RequestBody Employee employee) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		employee.setPId(null);
		employeeService.addEmployee(employee);
		return employee;
	}
	
	
	@ApiOperation(value = "更新 根据商机候选人标识更新商机候选人信息", notes = "根据商机候选人标识更新商机候选人信息")
	@RequestMapping(value = "/employee/{pId:.+}", method = RequestMethod.PUT)
	public Object modifyEmployeeById(HttpServletResponse response,
			@PathVariable Integer pId,
			@ApiParam(value = "employee", required = true) @RequestBody Employee employee
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Employee tempEmployee = employeeService.queryEmployeeByPId(pId);
		employee.setPId(pId);
		if(null == tempEmployee){
			throw new NotFoundException("商机候选人");
		}
		return employeeService.modifyEmployee(employee);
	}

	@ApiOperation(value = "删除 根据商机候选人标识删除商机候选人信息", notes = "根据商机候选人标识删除商机候选人信息")
	@RequestMapping(value = "/employee/{pId:.+}", method = RequestMethod.DELETE)
	public Object dropEmployeeByPId(HttpServletResponse response, @PathVariable Integer pId) {
		Employee employee = employeeService.queryEmployeeByPId(pId);
		if(null == employee){
			throw new NotFoundException("商机候选人");
		}
		return employeeService.dropEmployeeByPId(pId);
	}
	
	@ApiOperation(value = "查询 根据商机候选人标识查询商机候选人信息", notes = "根据商机候选人标识查询商机候选人信息")
	@RequestMapping(value = "/employee/{pId:.+}", method = RequestMethod.GET)
	public Object queryEmployeeById(HttpServletResponse response,
			@PathVariable Integer pId) {
		Employee employee = employeeService.queryEmployeeByPId(pId);
		if(null == employee){
			throw new NotFoundException("商机候选人");
		}
		return employee;
	}
	
	@ApiOperation(value = "查询 根据商机候选人属性查询商机候选人信息列表", notes = "根据商机候选人属性查询商机候选人信息列表")
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public Object queryEmployeeList(HttpServletResponse response,
			Employee employee) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return employeeService.queryEmployeeByProperty(ClassUtil.transBean2Map(employee, false));
	}
	
	@ApiOperation(value = "商机下发量全体归零", notes = "商机下发量全体归零")
	@RequestMapping(value = "/employees", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object zeroEmployee(HttpServletResponse response, HttpServletRequest request) {
		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
		if(ui.getUserInfo()==null) {
			throw new AuthorityException("用户权限错误");
		}
		if(ui.getUserInfo()!=null && ui.getUserInfo().getUlevel()!=null && ui.getUserInfo().getUlevel().intValue()==9) {
			return employeeService.modifyEmployeeTotalZero();
		}
		throw new AuthorityException("无权限");
	}
	
	@ApiOperation(value = "查询分页 根据商机候选人属性分页查询商机候选人信息列表", notes = "根据商机候选人属性分页查询商机候选人信息列表")
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public Object queryEmployeePage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, Employee employee) {				
		return employeeService.queryEmployeeForPage(pagenum, pagesize, sort, employee);
	}

}
