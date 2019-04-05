package com.hnjing.core.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: UserInfo
 * @Description: 用户信息表实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月26日 10时19分
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Length(min=0, max=8, message="{org.hibernate.validator.constraints.Length.message}")
	private String ucode;	//tb_user_info:ucode  用户工号  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String uname;	//tb_user_info:uname  用户姓名  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String umail;	//tb_user_info:umail  用户邮箱  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private Integer ustatus;	//tb_user_info:ustatus  用户状态0有效1无效  

	private Integer ulevel;	//tb_user_info:ulevel  用户等级 1线索2商机3线索商机7管理  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String token;	//tb_user_info:token  用户授权码  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private Date lastDt;	//tb_user_info:last_dt  授权权日期  

	private java.sql.Timestamp gmtCreated;	//tb_user_info:gmt_created  创建时间  

	@Length(min=0, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdNo;	//tb_user_info:created_no  创建人员  

	private java.sql.Timestamp gmtModify;	//tb_user_info:gmt_modify  修订时间  

	@Length(min=0, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String modifyNo;	//tb_user_info:modify_no  修订人员  


	/**
	* @DatabasetableColumnName: tb_user_info:ucode
	* @Description: 获取属性        用户工号
	* @return: String
	*/
	public String getUcode(){
		return ucode;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:ucode
	* @Description: 设置属性        用户工号
	* @return: String
	*/
	public void setUcode(String ucode){
		this.ucode = ucode;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:uname
	* @Description: 获取属性        用户姓名
	* @return: String
	*/
	public String getUname(){
		return uname;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:uname
	* @Description: 设置属性        用户姓名
	* @return: String
	*/
	public void setUname(String uname){
		this.uname = uname;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:umail
	* @Description: 获取属性        用户邮箱
	* @return: String
	*/
	public String getUmail(){
		return umail;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:umail
	* @Description: 设置属性        用户邮箱
	* @return: String
	*/
	public void setUmail(String umail){
		this.umail = umail;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:ustatus
	* @Description: 获取属性        用户状态0有效1无效
	* @return: Integer
	*/
	public Integer getUstatus(){
		return ustatus;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:ustatus
	* @Description: 设置属性        用户状态0有效1无效
	* @return: Integer
	*/
	public void setUstatus(Integer ustatus){
		this.ustatus = ustatus;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:ulevel
	* @Description: 获取属性        用户等级 1线索2商机3线索商机7管理
	* @return: Integer
	*/
	public Integer getUlevel(){
		return ulevel;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:ulevel
	* @Description: 设置属性        用户等级 1线索2商机3线索商机7管理
	* @return: Integer
	*/
	public void setUlevel(Integer ulevel){
		this.ulevel = ulevel;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:token
	* @Description: 获取属性        用户授权码
	* @return: String
	*/
	public String getToken(){
		return token;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:token
	* @Description: 设置属性        用户授权码
	* @return: String
	*/
	public void setToken(String token){
		this.token = token;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:last_dt
	* @Description: 获取属性        授权权日期
	* @return: Date
	*/
	public Date getLastDt(){
		return lastDt;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:last_dt
	* @Description: 设置属性        授权权日期
	* @return: Date
	*/
	public void setLastDt(Date lastDt){
		this.lastDt = lastDt;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:gmt_created
	* @Description: 获取属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:gmt_created
	* @Description: 设置属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:created_no
	* @Description: 获取属性        创建人员
	* @return: String
	*/
	public String getCreatedNo(){
		return createdNo;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:created_no
	* @Description: 设置属性        创建人员
	* @return: String
	*/
	public void setCreatedNo(String createdNo){
		this.createdNo = createdNo;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:gmt_modify
	* @Description: 获取属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtModify(){
		return gmtModify;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:gmt_modify
	* @Description: 设置属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtModify(java.sql.Timestamp gmtModify){
		this.gmtModify = gmtModify;	
	}	
	/**
	* @DatabasetableColumnName: tb_user_info:modify_no
	* @Description: 获取属性        修订人员
	* @return: String
	*/
	public String getModifyNo(){
		return modifyNo;	
	}
	
	/**
	* @DatabasetableColumnName: tb_user_info:modify_no
	* @Description: 设置属性        修订人员
	* @return: String
	*/
	public void setModifyNo(String modifyNo){
		this.modifyNo = modifyNo;	
	}	
	
	
	
	
}

