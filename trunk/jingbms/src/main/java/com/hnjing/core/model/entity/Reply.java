package com.hnjing.core.model.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
 * @ClassName: Reply
 * @Description: 销售回复实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 17时06分
 */
public class Reply implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer id;	//tb_reply:id    

	private Integer bId;	//tb_reply:b_id    

	@Length(min=0, max=1024, message="{org.hibernate.validator.constraints.Length.message}")
	private String content;	//tb_reply:content  回复内容  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String employee;	//tb_reply:employee  回复员工  

	private java.sql.Timestamp gmtCreated;	//tb_reply:gmt_created  创建时间  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdMan;	//tb_reply:created_man  提交员工  


	/**
	* @DatabasetableColumnName: tb_reply:id
	* @Description: 获取属性        
	* @return: Integer
	*/
	public Integer getId(){
		return id;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:id
	* @Description: 设置属性        
	* @return: Integer
	*/
	public void setId(Integer id){
		this.id = id;	
	}	
	/**
	* @DatabasetableColumnName: tb_reply:b_id
	* @Description: 获取属性        
	* @return: Integer
	*/
	public Integer getBId(){
		return bId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:b_id
	* @Description: 设置属性        
	* @return: Integer
	*/
	public void setBId(Integer bId){
		this.bId = bId;	
	}	
	/**
	* @DatabasetableColumnName: tb_reply:content
	* @Description: 获取属性        回复内容
	* @return: String
	*/
	public String getContent(){
		return content;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:content
	* @Description: 设置属性        回复内容
	* @return: String
	*/
	public void setContent(String content){
		this.content = content;	
	}	
	/**
	* @DatabasetableColumnName: tb_reply:employee
	* @Description: 获取属性        回复员工
	* @return: String
	*/
	public String getEmployee(){
		return employee;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:employee
	* @Description: 设置属性        回复员工
	* @return: String
	*/
	public void setEmployee(String employee){
		this.employee = employee;	
	}	
	/**
	* @DatabasetableColumnName: tb_reply:gmt_created
	* @Description: 获取属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:gmt_created
	* @Description: 设置属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	/**
	* @DatabasetableColumnName: tb_reply:created_man
	* @Description: 获取属性        提交员工
	* @return: String
	*/
	public String getCreatedMan(){
		return createdMan;	
	}
	
	/**
	* @DatabasetableColumnName: tb_reply:created_man
	* @Description: 设置属性        提交员工
	* @return: String
	*/
	public void setCreatedMan(String createdMan){
		this.createdMan = createdMan;	
	}	
	
	
	
	
}

