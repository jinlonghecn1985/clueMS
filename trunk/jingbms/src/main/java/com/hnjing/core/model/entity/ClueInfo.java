package com.hnjing.core.model.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: ClueInfo
 * @Description: 线索信息实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
public class ClueInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer cId;	//tb_clue_info:c_id  线索标识  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String cSource;	//tb_clue_info:c_source  线索来源，详细参见数据字典clueSource  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String cMan;	//tb_clue_info:c_man  联系人  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=16, message="{org.hibernate.validator.constraints.Length.message}")
	@Pattern(regexp="((\\d{3,4}-)[0-9]{7,8}(-[0-9]{0,4})?)", message="{javax.validation.constraints.Pattern.message}")
	private String cPhone;	//tb_clue_info:c_phone  联系电话  

	@Length(min=0, max=1024, message="{org.hibernate.validator.constraints.Length.message}")
	private String cMessage;	//tb_clue_info:c_message  线索留言  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String department;	//tb_clue_info:department  部门  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String employee;	//tb_clue_info:employee  员工  

	@Length(min=0, max=1024, message="{org.hibernate.validator.constraints.Length.message}")
	private String cGoods;	//tb_clue_info:c_goods  意向产品  

	@Length(min=0, max=64, message="{org.hibernate.validator.constraints.Length.message}")
	private String cCity;	//tb_clue_info:c_city  线索区域 详细参见字典clueCity  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String cType;	//tb_clue_info:c_type  咨询方式 参见字典clueType  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=16, message="{org.hibernate.validator.constraints.Length.message}")
	private String cStatus;	//tb_clue_info:c_status  线索状态 0新增 1商机 2线索 11无效咨询 21禁止合作  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String cReason;	//tb_clue_info:c_reason  判定补充  

	@Length(min=0, max=512, message="{org.hibernate.validator.constraints.Length.message}")
	private String cCustomer;	//tb_clue_info:c_customer  客户企业  

	@Length(min=0, max=1024, message="{org.hibernate.validator.constraints.Length.message}")
	private String cNote;	//tb_clue_info:c_note  沟通备注
	
	@Length(min=1, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String param1;
	
	@Length(min=1, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String param2;
	
	private Integer statuss;

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private java.sql.Timestamp gmtCreated;	//tb_clue_info:gmt_created  创建时间  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdNo;	//tb_clue_info:created_no  创建人  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private java.sql.Timestamp gmtModify;	//tb_clue_info:gmt_modify  修订时间  

	@Length(min=0, max=255, message="{org.hibernate.validator.constraints.Length.message}")
	private String modifyNo;	//tb_clue_info:modify_no  修订人员  


	/**
	* @DatabasetableColumnName: tb_clue_info:c_id
	* @Description: 获取属性        线索标识
	* @return: Integer
	*/
	public Integer getCId(){
		return cId;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_id
	* @Description: 设置属性        线索标识
	* @return: Integer
	*/
	public void setCId(Integer cId){
		this.cId = cId;	
	}	
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
	* @DatabasetableColumnName: tb_clue_info:c_man
	* @Description: 获取属性        联系人
	* @return: String
	*/
	public String getCMan(){
		return cMan;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_man
	* @Description: 设置属性        联系人
	* @return: String
	*/
	public void setCMan(String cMan){
		this.cMan = cMan;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_phone
	* @Description: 获取属性        联系电话
	* @return: String
	*/
	public String getCPhone(){
		return cPhone;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_phone
	* @Description: 设置属性        联系电话
	* @return: String
	*/
	public void setCPhone(String cPhone){
		this.cPhone = cPhone;	
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
	public String getDepartment(){
		return department;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:department
	* @Description: 设置属性        部门
	* @return: String
	*/
	public void setDepartment(String department){
		this.department = department;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:employee
	* @Description: 获取属性        员工
	* @return: String
	*/
	public String getEmployee(){
		return employee;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:employee
	* @Description: 设置属性        员工
	* @return: String
	*/
	public void setEmployee(String employee){
		this.employee = employee;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_goods
	* @Description: 获取属性        意向产品
	* @return: String
	*/
	public String getCGoods(){
		return cGoods;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_goods
	* @Description: 设置属性        意向产品
	* @return: String
	*/
	public void setCGoods(String cGoods){
		this.cGoods = cGoods;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_city
	* @Description: 获取属性        线索区域 详细参见字典clueCity
	* @return: String
	*/
	public String getCCity(){
		return cCity;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_city
	* @Description: 设置属性        线索区域 详细参见字典clueCity
	* @return: String
	*/
	public void setCCity(String cCity){
		this.cCity = cCity;	
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
	* @DatabasetableColumnName: tb_clue_info:c_status
	* @Description: 获取属性        线索状态 0新增 1商机 2线索 11无效咨询 21禁止合作
	* @return: String
	*/
	public String getCStatus(){
		return cStatus;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_status
	* @Description: 设置属性        线索状态 0新增 1商机 2线索 11无效咨询 21禁止合作
	* @return: String
	*/
	public void setCStatus(String cStatus){
		this.cStatus = cStatus;	
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
	* @DatabasetableColumnName: tb_clue_info:c_customer
	* @Description: 获取属性        客户企业
	* @return: String
	*/
	public String getCCustomer(){
		return cCustomer;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:c_customer
	* @Description: 设置属性        客户企业
	* @return: String
	*/
	public void setCCustomer(String cCustomer){
		this.cCustomer = cCustomer;	
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
	* @DatabasetableColumnName: tb_clue_info:gmt_created
	* @Description: 获取属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtCreated(){
		return gmtCreated;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:gmt_created
	* @Description: 设置属性        创建时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtCreated(java.sql.Timestamp gmtCreated){
		this.gmtCreated = gmtCreated;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:created_no
	* @Description: 获取属性        创建人
	* @return: String
	*/
	public String getCreatedNo(){
		return createdNo;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:created_no
	* @Description: 设置属性        创建人
	* @return: String
	*/
	public void setCreatedNo(String createdNo){
		this.createdNo = createdNo;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:gmt_modify
	* @Description: 获取属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public java.sql.Timestamp getGmtModify(){
		return gmtModify;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:gmt_modify
	* @Description: 设置属性        修订时间
	* @return: java.sql.Timestamp
	*/
	public void setGmtModify(java.sql.Timestamp gmtModify){
		this.gmtModify = gmtModify;	
	}	
	/**
	* @DatabasetableColumnName: tb_clue_info:modify_no
	* @Description: 获取属性        修订人员
	* @return: String
	*/
	public String getModifyNo(){
		return modifyNo;	
	}
	
	/**
	* @DatabasetableColumnName: tb_clue_info:modify_no
	* @Description: 设置属性        修订人员
	* @return: String
	*/
	public void setModifyNo(String modifyNo){
		this.modifyNo = modifyNo;	
	}

	/**
	 * @return the statuss
	 */
	public Integer getStatuss() {
		return statuss;
	}

	/**
	 * @param statuss the statuss to set
	 */
	public void setStatuss(Integer statuss) {
		this.statuss = statuss;
	}

	/**
	 * @return the param1
	 */
	public String getParam1() {
		return param1;
	}

	/**
	 * @param param1 the param1 to set
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}

	/**
	 * @return the param2
	 */
	public String getParam2() {
		return param2;
	}

	/**
	 * @param param2 the param2 to set
	 */
	public void setParam2(String param2) {
		this.param2 = param2;
	}	
	
	
	
	
}

