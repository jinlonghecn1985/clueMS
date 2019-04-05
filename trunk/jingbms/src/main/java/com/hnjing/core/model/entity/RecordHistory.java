package com.hnjing.core.model.entity;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * @ClassName: RecordHistory
 * @Description: 操作记录实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public class RecordHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer oId;	//tb_record_history:o_id  操作标识  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String cName;	//tb_record_history:c_name  客户名称  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	@Pattern(regexp="((\\d{3,4}-)[0-9]{7,8}(-[0-9]{0,4})?)", message="{javax.validation.constraints.Pattern.message}")
	private String cPhone;	//tb_record_history:c_phone  客户电话  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String eDepartment;	//tb_record_history:e_department  受理部门  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String eName;	//tb_record_history:e_name  受理人员  

	private java.lang.Object content;	//tb_record_history:content  操作内容  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdMan;	//tb_record_history:created_man  操作人员  

	private java.sql.Timestamp gmtCreated;	//tb_record_history:gmt_created  操作时间  


	/**
	* @DatabasetableColumnName: tb_record_history:o_id
	* @Description: 获取属性        操作标识
	* @return: Integer
	*/
	public Integer getOId(){
		return oId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:o_id
	* @Description: 设置属性        操作标识
	* @return: Integer
	*/
	public void setOId(Integer oId){
		this.oId = oId;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:c_name
	* @Description: 获取属性        客户名称
	* @return: String
	*/
	public String getCName(){
		return cName;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:c_name
	* @Description: 设置属性        客户名称
	* @return: String
	*/
	public void setCName(String cName){
		this.cName = cName;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:c_phone
	* @Description: 获取属性        客户电话
	* @return: String
	*/
	public String getCPhone(){
		return cPhone;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:c_phone
	* @Description: 设置属性        客户电话
	* @return: String
	*/
	public void setCPhone(String cPhone){
		this.cPhone = cPhone;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:e_department
	* @Description: 获取属性        受理部门
	* @return: String
	*/
	public String getEDepartment(){
		return eDepartment;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:e_department
	* @Description: 设置属性        受理部门
	* @return: String
	*/
	public void setEDepartment(String eDepartment){
		this.eDepartment = eDepartment;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:e_name
	* @Description: 获取属性        受理人员
	* @return: String
	*/
	public String getEName(){
		return eName;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:e_name
	* @Description: 设置属性        受理人员
	* @return: String
	*/
	public void setEName(String eName){
		this.eName = eName;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:content
	* @Description: 获取属性        操作内容
	* @return: java.lang.Object
	*/
	public java.lang.Object getContent(){
		return content;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:content
	* @Description: 设置属性        操作内容
	* @return: java.lang.Object
	*/
	public void setContent(java.lang.Object content){
		this.content = content;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:created_man
	* @Description: 获取属性        操作人员
	* @return: String
	*/
	public String getCreatedMan(){
		return createdMan;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:created_man
	* @Description: 设置属性        操作人员
	* @return: String
	*/
	public void setCreatedMan(String createdMan){
		this.createdMan = createdMan;	
	}	
	/**
	* @DatabasetableColumnName: tb_record_history:gmt_created
	* @Description: 获取属性        操作时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_record_history:gmt_created
	* @Description: 设置属性        操作时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	
	
	
	
}

