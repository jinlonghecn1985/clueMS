/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BusinessInfoBo.java
 * @Prject: BMS
 * @Package: com.hnjing.core.service.impl.bo
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年4月4日 下午2:05:01
 * @version: V1.0  
 */
package com.hnjing.core.service.impl.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.hnjing.core.model.entity.BusinessInfo;

/**
 * @ClassName: BusinessInfoBo
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2019年4月4日 下午2:05:01
 */
public class BusinessInfoBo extends BusinessInfo {
private static final long serialVersionUID = 1L;	
 

	private String cSource;	//tb_clue_info:c_source  线索来源，详细参见数据字典clueSource 
	
	private String cMessage;	//tb_clue_info:c_message  线索留言	
	
	private String cMan;

	private String fromDepartment;	//tb_clue_info:department  部门  

	private String fromEmployee;	//tb_clue_info:employee  员工 
	
	private String cType;	//tb_clue_info:c_type  咨询方式 参见字典clueType  

	private String cReason;	//tb_clue_info:c_reason  判定补充  

	private String cNote;	//tb_clue_info:c_note  沟通备注
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_source
	* @Description: 获取属性        线索来源，详细参见数据字典clueSource
	* @return: String
	*/
	public String getCSource(){
		return cSource;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_source
	* @Description: 设置属性        线索来源，详细参见数据字典clueSource
	* @return: String
	*/
	public void setCSource(String cSource){
		this.cSource = cSource;	
	}	

	/**
	* @DatabasetableColumnName: tb_clue_info:c_message
	* @Description: 获取属性        线索留言
	* @return: String
	*/
	public String getCMessage(){
		return cMessage;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_message
	* @Description: 设置属性        线索留言
	* @return: String
	*/
	public void setCMessage(String cMessage){
		this.cMessage = cMessage;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:department
	* @Description: 获取属性        部门
	* @return: String
	*/
	public String getFromDepartment(){
		return fromDepartment;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:department
	* @Description: 设置属性        部门
	* @return: String
	*/
	public void setFromDepartment(String fromDepartment){
		this.fromDepartment = fromDepartment;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:employee
	* @Description: 获取属性        员工
	* @return: String
	*/
	public String getFromEmployee(){
		return fromEmployee;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:employee
	* @Description: 设置属性        员工
	* @return: String
	*/
	public void setFromEmployee(String fromEmployee){
		this.fromEmployee = fromEmployee;
	}
	/**
	* @DatabasetableColumnName: tb_clue_info:c_type
	* @Description: 获取属性        咨询方式 参见字典clueType
	* @return: String
	*/
	public String getCType(){
		return cType;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_type
	* @Description: 设置属性        咨询方式 参见字典clueType
	* @return: String
	*/
	public void setCType(String cType){
		this.cType = cType;	
	}	
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_reason
	* @Description: 获取属性        判定补充
	* @return: String
	*/
	public String getCReason(){
		return cReason;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_reason
	* @Description: 设置属性        判定补充
	* @return: String
	*/
	public void setCReason(String cReason){
		this.cReason = cReason;	
	}	
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_note
	* @Description: 获取属性        沟通备注
	* @return: String
	*/
	public String getCNote(){
		return cNote;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_note
	* @Description: 设置属性        沟通备注
	* @return: String
	*/
	public void setCNote(String cNote){
		this.cNote = cNote;	
	}

	/**
	 * @return the cMan
	 */
	public String getcMan() {
		return cMan;
	}

	/**
	 * @param cMan the cMan to set
	 */
	public void setcMan(String cMan) {
		this.cMan = cMan;
	}		
	
}
