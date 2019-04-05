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


import com.hnjing.core.model.entity.Employee;
import com.hnjing.core.model.dao.EmployeeMapper;
import com.hnjing.core.service.EmployeeService;

/**
 * @ClassName: Employee
 * @Description: 表tb_employee服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 15时15分
 */
@Service("employeeService")
@Transactional(readOnly=true)
public class  EmployeeServiceImpl implements EmployeeService {	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
    private EmployeeMapper employeeMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addEmployee
	 * @Description:添加表tb_employee
	 * @param employee 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Employee addEmployee(Employee employee){
		int ret = employeeMapper.addEmployee(employee);
		if(ret>0){
			return employee;
		}
		return null;
	}
	
	/**
	 * @Title modifyEmployee
	 * @Description:修改表tb_employee
	 * @param employee 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyEmployee(Employee employee){
		return employeeMapper.modifyEmployee(employee);
	}
	
	/**
	 * @Title: dropEmployeeByPId
	 * @Description:删除表tb_employee
	 * @param pId 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropEmployeeByPId(Integer pId){
		return employeeMapper.dropEmployeeByPId(pId);
	}
	
	/**
	 * @Title: queryEmployeeByPId
	 * @Description:根据实体标识查询表tb_employee
	 * @param pId 实体标识
	 * @return Employee
	 */
	@Override
	public Employee queryEmployeeByPId(Integer pId){
		return employeeMapper.queryEmployeeByPId(pId);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryEmployeeForPage
	 * @Description: 根据表tb_employee属性与分页信息分页查询表tb_employee信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param employee 实体
	 * @return List<Employee>
	 */
	@Override
	public Map<String, Object> queryEmployeeForPage(Integer pagenum, Integer pagesize, String sort, Employee employee){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, Employee.class);
		List<Employee> entityList = employeeMapper.queryEmployeeForPage(pageBounds, employee);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, Employee.class);
		}
		
		PageList<Employee> pagelist = (PageList<Employee>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	
	
	 
	/**
	 * @Title: queryEmployeeByProperty
	 * @Description:根据属性查询表tb_employee
	 * @return List<Employee>
	 */
	@Override
	public List<Employee> queryEmployeeByProperty(Map<String, Object> map){
		if(map==null) {
			map = new HashMap<String, Object>();
		}
		if(!map.containsKey("personStatus")) {
			map.put("personStatus", 0);
		}
		return employeeMapper.queryEmployeeByProperty(map);
	}

	/*
	 * @Title: modifyEmployeeTotalZero
	 * @Description: TODO
	 * @param @return    参数  
	 * @author Jinlong He
	 * @return
	 * @see com.hnjing.core.service.EmployeeService#modifyEmployeeTotalZero()
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Integer modifyEmployeeTotalZero() {
		return employeeMapper.modifyEmployeeTotalZero();
	}


}
