package com.hnjing.core.service;

import java.util.List;
import java.util.Map;

import com.hnjing.core.model.entity.Employee;

/**
 * @ClassName: Employee
 * @Description: 商机候选人服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public interface EmployeeService {

	/**
	 * @Title: addEmployee
	 * @Description:添加商机候选人
	 * @param employee
	 *            实体
	 * @return Integer
	 */
	Employee addEmployee(Employee employee);

	/**
	 * @Title modifyEmployee
	 * @Description:修改商机候选人
	 * @param employee
	 *            实体
	 * @return Integer
	 */
	Integer modifyEmployee(Employee employee);

	/**
	 * @Title: dropEmployeeByPId
	 * @Description:删除商机候选人
	 * @param pId
	 *            实体标识
	 * @return Integer
	 */
	Integer dropEmployeeByPId(Integer pId);

	/**
	 * @Title: queryEmployeeByPId
	 * @Description:根据实体标识查询商机候选人
	 * @param pId
	 *            实体标识
	 * @return Employee
	 */
	Employee queryEmployeeByPId(Integer pId);

	/**
	 * @Title: queryEmployeeForPage
	 * @Description: 根据商机候选人属性与分页信息分页查询商机候选人信息
	 * @param pagenum
	 *            页
	 * @param pagesize
	 *            页大小
	 * @param sort
	 *            排序
	 * @param employee
	 *            实体
	 * @return List<Employee>
	 */
	Map<String, Object> queryEmployeeForPage(Integer pagenum, Integer pagesize, String sort, Employee employee);

	/**
	 * @Title: queryEmployeeByProperty
	 * @Description:根据属性查询商机候选人
	 * @return List<Employee>
	 */
	List<Employee> queryEmployeeByProperty(Map<String, Object> map);

	/** 
	* @Title: modifyEmployeeTotalZero 
	* @Description: 周期内商机下发量归零
	* @return  
	* Integer    返回类型 
	* @throws 
	*/
	Integer modifyEmployeeTotalZero();

}
