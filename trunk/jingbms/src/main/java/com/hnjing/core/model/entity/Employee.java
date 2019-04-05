package com.hnjing.core.model.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
 * @ClassName: Employee
 * @Description: 商机候选人实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer pId;	//tb_employee:p_id  人员标识  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String personName;	//tb_employee:person_name  员工姓名  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String personMail;	//tb_employee:person_mail  邮件地址  

	@Length(min=0, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String personDep;	//tb_employee:person_dep  员工部门  

	@Length(min=0, max=256, message="{org.hibernate.validator.constraints.Length.message}")
	private String mailOther;	//tb_employee:mail_other  抄送人员  

	@Length(min=0, max=256, message="{org.hibernate.validator.constraints.Length.message}")
	private String param1;	//tb_employee:param1  备用参数  

	@Length(min=0, max=256, message="{org.hibernate.validator.constraints.Length.message}")
	private String param2;	//tb_employee:param2  备用参数  

	private Integer personStatus;	//tb_employee:person_status  员工状态0有效1失效  

	private java.sql.Timestamp gmtCreated;	//tb_employee:gmt_created  创建时间  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdMan;	//tb_employee:created_man  创建人员  

	private java.sql.Timestamp gmtModify;	//tb_employee:gmt_modify  修订时间  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String modifyMan;	//tb_employee:modify_man  修订人员 
	
	private Integer totalCount; //周期内商机下发量


	/**
	* @DatabasetableColumnName: tb_employee:p_id
	* @Description: 获取属性        人员标识
	* @return: Integer
	*/
	public Integer getPId(){
		return pId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:p_id
	* @Description: 设置属性        人员标识
	* @return: Integer
	*/
	public void setPId(Integer pId){
		this.pId = pId;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:person_name
	* @Description: 获取属性        员工姓名
	* @return: String
	*/
	public String getPersonName(){
		return personName;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:person_name
	* @Description: 设置属性        员工姓名
	* @return: String
	*/
	public void setPersonName(String personName){
		this.personName = personName;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:person_mail
	* @Description: 获取属性        邮件地址
	* @return: String
	*/
	public String getPersonMail(){
		return personMail;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:person_mail
	* @Description: 设置属性        邮件地址
	* @return: String
	*/
	public void setPersonMail(String personMail){
		this.personMail = personMail;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:person_dep
	* @Description: 获取属性        员工部门
	* @return: String
	*/
	public String getPersonDep(){
		return personDep;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:person_dep
	* @Description: 设置属性        员工部门
	* @return: String
	*/
	public void setPersonDep(String personDep){
		this.personDep = personDep;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:mail_other
	* @Description: 获取属性        抄送人员
	* @return: String
	*/
	public String getMailOther(){
		return mailOther;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:mail_other
	* @Description: 设置属性        抄送人员
	* @return: String
	*/
	public void setMailOther(String mailOther){
		this.mailOther = mailOther;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:param1
	* @Description: 获取属性        备用参数
	* @return: String
	*/
	public String getParam1(){
		return param1;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:param1
	* @Description: 设置属性        备用参数
	* @return: String
	*/
	public void setParam1(String param1){
		this.param1 = param1;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:param2
	* @Description: 获取属性        备用参数
	* @return: String
	*/
	public String getParam2(){
		return param2;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:param2
	* @Description: 设置属性        备用参数
	* @return: String
	*/
	public void setParam2(String param2){
		this.param2 = param2;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:person_status
	* @Description: 获取属性        员工状态0有效1失效
	* @return: Integer
	*/
	public Integer getPersonStatus(){
		return personStatus;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:person_status
	* @Description: 设置属性        员工状态0有效1失效
	* @return: Integer
	*/
	public void setPersonStatus(Integer personStatus){
		this.personStatus = personStatus;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:gmt_created
	* @Description: 获取属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:gmt_created
	* @Description: 设置属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:created_man
	* @Description: 获取属性        创建人员
	* @return: String
	*/
	public String getCreatedMan(){
		return createdMan;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:created_man
	* @Description: 设置属性        创建人员
	* @return: String
	*/
	public void setCreatedMan(String createdMan){
		this.createdMan = createdMan;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:gmt_modify
	* @Description: 获取属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtModify(){
		return gmtModify;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:gmt_modify
	* @Description: 设置属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtModify(java.sql.Timestamp gmtModify){
		this.gmtModify = gmtModify;	
	}	
	/**
	* @DatabasetableColumnName: tb_employee:modify_man
	* @Description: 获取属性        修订人员
	* @return: String
	*/
	public String getModifyMan(){
		return modifyMan;	
	}
	
	/**
	* @DatabasetableColumnName: tb_employee:modify_man
	* @Description: 设置属性        修订人员
	* @return: String
	*/
	public void setModifyMan(String modifyMan){
		this.modifyMan = modifyMan;	
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}	
	
	
	
	
}

