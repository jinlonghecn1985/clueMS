package com.hnjing.core.model.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: BusinessInfo
 * @Description: 商机信息实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月20日 14时16分
 */
public class BusinessInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer bId;	//tb_business_info:b_id  客户标识  

	private Integer clueId;	//tb_business_info:clue_id  线索标识  

	@Length(min=0, max=16, message="{org.hibernate.validator.constraints.Length.message}")
	private String erpId;	//tb_business_info:erp_id  ERP标识  

	@Length(min=0, max=16, message="{org.hibernate.validator.constraints.Length.message}")
	private String validInfo;	//tb_business_info:valid_info  商机类型  

	@Length(min=0, max=512, message="{org.hibernate.validator.constraints.Length.message}")
	private String cCustomer;	//tb_business_info:entity  客户名称 
	
	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=16, message="{org.hibernate.validator.constraints.Length.message}")
	@Pattern(regexp="((\\d{3,4}-)[0-9]{7,8}(-[0-9]{0,4})?)", message="{javax.validation.constraints.Pattern.message}")
	private String cPhone;	//tb_clue_info:c_phone  联系电话 

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String city;	//tb_business_info:city  归属城市  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private Integer hasIn;	//tb_business_info:has_in  商机来源 0库内1库外  

	private Integer isNew;	//tb_business_info:is_new  客户类型 0新客1老客  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String identifier;	//tb_business_info:identifier  企业代码  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String employee;	//tb_business_info:employee  联系人  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String department;	//tb_business_info:department  跟进人所在部门  

	//@Length(min=0, max=1024, message="{org.hibernate.validator.constraints.Length.message}")
	private String cGoods;	//tb_clue_info:c_goods  意向产品 
	
	@Length(min=0, max=512, message="{org.hibernate.validator.constraints.Length.message}")
	private String business;	//tb_business_info:business  成交产品  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private Integer sjStatus;	//tb_business_info:sj_status  商机状态 0跟进中 7已关闭 8已合作  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String industryJw;	//tb_business_info:industry_jw  竞网行业  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String industry1;	//tb_business_info:industry1  一级行业  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String industry2;	//tb_business_info:industry2  二级行业  

	@Length(min=0, max=512, message="{org.hibernate.validator.constraints.Length.message}")
	private String bnote;	//tb_business_info:bnote  备注  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private java.sql.Timestamp gmtCreated;	//tb_business_info:gmt_created  创建时间  

	private String gmtCreatedUser;	//tb_business_info:gmt_created_user  创建人  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private java.sql.Timestamp gmtModify;	//tb_business_info:gmt_modify  修订时间  

	private String gmtModifyUser;	//tb_business_info:gmt_modify_user  修订人员  


	/**
	* @DatabasetableColumnName: tb_business_info:b_id
	* @Description: 获取属性        客户标识
	* @return: Integer
	*/
	public Integer getBId(){
		return bId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:b_id
	* @Description: 设置属性        客户标识
	* @return: Integer
	*/
	public void setBId(Integer bId){
		this.bId = bId;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:clue_id
	* @Description: 获取属性        线索标识
	* @return: String
	*/
	public Integer getClueId(){
		return clueId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:clue_id
	* @Description: 设置属性        线索标识
	* @return: String
	*/
	public void setClueId(Integer clueId){
		this.clueId = clueId;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:erp_id
	* @Description: 获取属性        ERP标识
	* @return: String
	*/
	public String getErpId(){
		return erpId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:erp_id
	* @Description: 设置属性        ERP标识
	* @return: String
	*/
	public void setErpId(String erpId){
		this.erpId = erpId;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:valid_info
	* @Description: 获取属性        商机类型
	* @return: String
	*/
	public String getValidInfo(){
		return validInfo;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:valid_info
	* @Description: 设置属性        商机类型
	* @return: String
	*/
	public void setValidInfo(String validInfo){
		this.validInfo = validInfo;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:cCustomer
	* @Description: 获取属性        客户名称
	* @return: String
	*/
	public String getCCustomer(){
		return cCustomer;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:getCCustomer
	* @Description: 设置属性        客户名称
	* @return: String
	*/
	public void setCCustomer(String cCustomer){
		this.cCustomer = cCustomer;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:city
	* @Description: 获取属性        归属城市
	* @return: String
	*/
	public String getCity(){
		return city;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:city
	* @Description: 设置属性        归属城市
	* @return: String
	*/
	public void setCity(String city){
		this.city = city;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:has_in
	* @Description: 获取属性        商机来源 0库内1库外
	* @return: Integer
	*/
	public Integer getHasIn(){
		return hasIn;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:has_in
	* @Description: 设置属性        商机来源 0库内1库外
	* @return: Integer
	*/
	public void setHasIn(Integer hasIn){
		this.hasIn = hasIn;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:is_new
	* @Description: 获取属性        客户类型 0新客1老客
	* @return: Integer
	*/
	public Integer getIsNew(){
		return isNew;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:is_new
	* @Description: 设置属性        客户类型 0新客1老客
	* @return: Integer
	*/
	public void setIsNew(Integer isNew){
		this.isNew = isNew;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:identifier
	* @Description: 获取属性        企业代码
	* @return: String
	*/
	public String getIdentifier(){
		return identifier;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:identifier
	* @Description: 设置属性        企业代码
	* @return: String
	*/
	public void setIdentifier(String identifier){
		this.identifier = identifier;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:employee
	* @Description: 获取属性        联系人
	* @return: String
	*/
	public String getEmployee(){
		return employee;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:employee
	* @Description: 设置属性        联系人
	* @return: String
	*/
	public void setEmployee(String employee){
		this.employee = employee;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:department
	* @Description: 获取属性        跟进人所在部门
	* @return: String
	*/
	public String getDepartment(){
		return department;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:department
	* @Description: 设置属性        跟进人所在部门
	* @return: String
	*/
	public void setDepartment(String department){
		this.department = department;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:business
	* @Description: 获取属性        成交产品
	* @return: String
	*/
	public String getBusiness(){
		return business;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:business
	* @Description: 设置属性        成交产品
	* @return: String
	*/
	public void setBusiness(String business){
		this.business = business;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:sj_status
	* @Description: 获取属性        商机状态 0跟进中 7已关闭 8已合作
	* @return: Integer
	*/
	public Integer getSjStatus(){
		return sjStatus;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:sj_status
	* @Description: 设置属性        商机状态 0跟进中 7已关闭 8已合作
	* @return: Integer
	*/
	public void setSjStatus(Integer sjStatus){
		this.sjStatus = sjStatus;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:industry_jw
	* @Description: 获取属性        竞网行业
	* @return: String
	*/
	public String getIndustryJw(){
		return industryJw;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:industry_jw
	* @Description: 设置属性        竞网行业
	* @return: String
	*/
	public void setIndustryJw(String industryJw){
		this.industryJw = industryJw;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:industry1
	* @Description: 获取属性        一级行业
	* @return: String
	*/
	public String getIndustry1(){
		return industry1;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:industry1
	* @Description: 设置属性        一级行业
	* @return: String
	*/
	public void setIndustry1(String industry1){
		this.industry1 = industry1;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:industry2
	* @Description: 获取属性        二级行业
	* @return: String
	*/
	public String getIndustry2(){
		return industry2;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:industry2
	* @Description: 设置属性        二级行业
	* @return: String
	*/
	public void setIndustry2(String industry2){
		this.industry2 = industry2;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:bnote
	* @Description: 获取属性        备注
	* @return: String
	*/
	public String getBnote(){
		return bnote;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:bnote
	* @Description: 设置属性        备注
	* @return: String
	*/
	public void setBnote(String bnote){
		this.bnote = bnote;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_created
	* @Description: 获取属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_created
	* @Description: 设置属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_created_user
	* @Description: 获取属性        创建人
	* @return: String
	*/
	public String getGmtCreatedUser(){
		return gmtCreatedUser;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_created_user
	* @Description: 设置属性        创建人
	* @return: Integer
	*/
	public void setGmtCreatedUser(String gmtCreatedUser){
		this.gmtCreatedUser = gmtCreatedUser;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_modify
	* @Description: 获取属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtModify(){
		return gmtModify;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_modify
	* @Description: 设置属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtModify(java.sql.Timestamp gmtModify){
		this.gmtModify = gmtModify;	
	}	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_modify_user
	* @Description: 获取属性        修订人员
	* @return: String
	*/
	public String getGmtModifyUser(){
		return gmtModifyUser;	
	}
	
	/**
	* @DatabasetableColumnName: tb_business_info:gmt_modify_user
	* @Description: 设置属性        修订人员
	* @return: String
	*/
	public void setGmtModifyUser(String gmtModifyUser){
		this.gmtModifyUser = gmtModifyUser;	
	}

	/**
	 * @return the cPhone
	 */
	public String getcPhone() {
		return cPhone;
	}

	/**
	 * @param cPhone the cPhone to set
	 */
	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}

	/**
	 * @return the cGoods
	 */
	public String getcGoods() {
		return cGoods;
	}

	/**
	 * @param cGoods the cGoods to set
	 */
	public void setcGoods(String cGoods) {
		this.cGoods = cGoods;
	}	
	
	
	
	
}

