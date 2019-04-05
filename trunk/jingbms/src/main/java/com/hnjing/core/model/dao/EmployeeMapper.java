package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.Employee;

/**
 * @ClassName: EmployeeMapper
 * @Description: 商机候选人映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Mapper
public interface EmployeeMapper {

	/**
	 * @Title: addEmployee
	 * @Description:添加商机候选人
	 * @param employee 实体
	 * @return Integer
	 */
	Integer addEmployee(Employee employee);
	
	/**
	 * @Title modifyEmployee
	 * @Description:修改商机候选人
	 * @param employee 实体
	 * @return Integer
	 */
	Integer modifyEmployee(Employee employee);
	
	/** 
	* @Title: modifyEmployeeTotalZero 
	* @Description: 周期内商机下发量归零
	* @return  
	* Integer    返回类型 
	* @throws 
	*/
	Integer modifyEmployeeTotalZero();
	
	/**
	 * @Title: dropEmployeeByPId
	 * @Description:删除商机候选人
	 * @param pId 实体标识
	 * @return Integer
	 */
	Integer dropEmployeeByPId(Integer pId);
	
	/**
	 * @Title: queryEmployeeByPId
	 * @Description:根据实体标识查询商机候选人
	 * @param pId 实体标识
	 * @return Employee
	 */
	Employee queryEmployeeByPId(Integer pId);
	 
	/**
	 * @Title: queryEmployeeForPage
	 * @Description: 根据商机候选人属性与分页信息分页查询商机候选人信息
	 * @param pageBounds 分页信息
	 * @param employee 实体
	 * @return List<Employee>
	 */
	List<Employee> queryEmployeeForPage(PageBounds pageBounds, @Param("employee") Employee employee);
	 
	 /**
	  * @Title: queryEmployeeByProperty
	  * @Description:根据属性查询商机候选人
	  * @return List<Employee>
	  */
	 List<Employee> queryEmployeeByProperty(@Param("employee") Map<String, Object> map);
	 
	 
	 
}
