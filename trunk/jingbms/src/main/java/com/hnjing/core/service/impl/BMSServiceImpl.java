/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: BMSServiceImpl.java
 * @Prject: BMS
 * @Package: com.hnjing.core.service.impl
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年3月26日 上午9:54:57
 * @version: V1.0  
 */
package com.hnjing.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hnjing.config.web.exception.ParameterException;
import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.model.entity.Employee;
import com.hnjing.core.model.entity.Reply;
import com.hnjing.core.model.entity.UserInfo;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.BusinessInfoService;
import com.hnjing.core.service.ClueInfoService;
import com.hnjing.core.service.EmployeeService;
import com.hnjing.core.service.ReplyService;
import com.hnjing.core.service.UserInfoService;
import com.hnjing.utils.DateUtil;
import com.hnjing.utils.MailUtil;

/**
 * @ClassName: BMSServiceImpl
 * @Description: 商机管理实现类
 * @author: Jinlong He
 * @date: 2019年3月26日 上午9:54:57
 */
@Service("bmsService")
@Transactional(readOnly=true)
public class BMSServiceImpl implements BMSService{
	private static final Logger logger = LoggerFactory.getLogger(BMSServiceImpl.class);

	@Autowired
	private ClueInfoService clueInfoService;
	
	@Autowired
	private BusinessInfoService businessInfoService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private MailUtil mailUtil;
	/*
	 * @Title: saveClueInfo
	 * @Description: 新增线索
	 * @param @param clue
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param clue
	 * @return
	 * @see com.hnjing.core.service.BMSService#saveClueInfo(com.hnjing.core.model.entity.ClueInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object saveClueInfo(ClueInfo clueInfo) {
		boolean sj = false;
		clueInfo.setStatuss(3);
		if(clueInfo.getCStatus()!=null && clueInfo.getCStatus().contains(",")) {
			if(clueInfo.getCStatus().split(",")[0].length()==7) {
				sj = true;
				clueInfo.setStatuss(1);
			}
			clueInfo.setCStatus(clueInfo.getCStatus().split(",")[1]);			
		}	
		clueInfo = clueInfoService.addClueInfo(clueInfo);
		if(clueInfo!=null && clueInfo.getCId()!=null){
			if(sj) {
				BusinessInfo bi = new BusinessInfo();
				bi.setSjStatus(0);
				bi.setClueId(clueInfo.getCId());
				bi.setCCustomer(clueInfo.getCCustomer());
				bi.setcPhone(clueInfo.getCPhone());
				bi.setGmtCreatedUser(clueInfo.getCreatedNo());
				bi.setCity(clueInfo.getCCity());
				bi.setcGoods(clueInfo.getCGoods());
				businessInfoService.addBusinessInfo(bi);
				if(bi==null || bi.getBId()==null) {
					throw new ParameterException("business", "线索添加商机异常");
				}
			}
			return clueInfo;
		}
		return null;
	}
	
	/*
	 * @Title: saveClueInfo2
	 * @Description: 核查线索
	 * @param @param clueInfo    参数  
	 * @author Jinlong He
	 * @param clueInfo
	 * @see com.hnjing.core.service.BMSService#saveClueInfo2(com.hnjing.core.model.entity.ClueInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public ClueInfo saveClueInfo2(ClueInfo clueInfo) {
		boolean sj = false;
		clueInfo.setStatuss(3);
		if(clueInfo.getCStatus()!=null && clueInfo.getCStatus().contains(",")) {
			if(clueInfo.getCStatus().split(",")[0].length()==7) {
				sj = true;
				clueInfo.setStatuss(1);
			}
			clueInfo.setCStatus(clueInfo.getCStatus().split(",")[1]);			
		}	
		clueInfoService.modifyClueInfo(clueInfo);
		if(clueInfo!=null && clueInfo.getCId()!=null){
			if(sj) {
				BusinessInfo bi = new BusinessInfo();
				bi.setSjStatus(0);
				bi.setcGoods(clueInfo.getCGoods());
				bi.setCity(clueInfo.getCCity());
				bi.setClueId(clueInfo.getCId());
				bi.setCCustomer(clueInfo.getCCustomer());
				bi.setcPhone(clueInfo.getCPhone());
				bi.setGmtCreatedUser(clueInfo.getModifyNo());
				businessInfoService.addBusinessInfo(bi);
				if(bi==null || bi.getBId()==null) {
					throw new ParameterException("business", "线索添加商机异常");
				}
			}
			return clueInfo;
		}
		return null;
		
	}


	/*
	 * @Title: saveBusiness
	 * @Description: 商机下发
	 * @param @param business
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param business
	 * @return
	 * @see com.hnjing.core.service.BMSService#saveBusiness(com.hnjing.core.model.entity.BusinessInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object saveBusiness(BusinessInfo business, Employee employee) {
		ClueInfo cii = new ClueInfo();
		cii.setStatuss(2);
		cii.setCId(business.getClueId());
		cii.setModifyNo(business.getGmtModifyUser());
		clueInfoService.modifyClueInfo(cii);
		business.setSjStatus(1);
		businessInfoService.modifyBusinessInfo(business);
		
		//发送邮件通知
		if(employee!=null && employee.getPersonMail()!=null) {
			Employee em = new Employee();
			em.setPId(employee.getPId());
			em.setTotalCount(employee.getTotalCount()+1);
			employeeService.modifyEmployee(em);
			//有收件人
			doSendSJMail(business, employee);
		}
		return business;
	}	

	/*
	 * @Title: closeBusiness
	 * @Description: 商机关闭
	 * @param @param business
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param business
	 * @return
	 * @see com.hnjing.core.service.BMSService#closeBusiness(com.hnjing.core.model.entity.BusinessInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object closeBusiness(BusinessInfo business) {
		business.setSjStatus(7);
		ClueInfo cii = new ClueInfo();
		cii.setCId(business.getClueId());
		cii.setStatuss(4);
		cii.setModifyNo(business.getGmtModifyUser());
		clueInfoService.modifyClueInfo(cii);
		return businessInfoService.modifyBusinessInfo(business);
	}

	/*
	 * @Title: followBusiness
	 * @Description: 
	 * @param @param reply
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param reply
	 * @return
	 * @see com.hnjing.core.service.BMSService#followBusiness(com.hnjing.core.model.entity.Reply)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object followBusiness(Reply reply) {
		return replyService.addReply(reply);
	}

	/*
	 * @Title: getUserInfo
	 * @Description: 
	 * @param @param request
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param request
	 * @return
	 * @see com.hnjing.core.service.BMSService#getUserInfo(javax.servlet.http.HttpServletRequest)
	 */ 
	@Override
	public UserInfo getUserInfo(HttpServletRequest request) {
		if(request!=null) {
			String cookie = request.getHeader("cookie");
			if(cookie!=null && cookie.contains("bmsusertoken")) {
				int f = cookie.indexOf("bmsusertoken");
				String tok = cookie.substring(f+13, f+45);
				if(tok!=null && tok.length()==32) {
					UserInfo ui = userInfoService.queryUserInfoByToken(tok);
					if(ui!=null && ui.getUstatus()!=null && ui.getUstatus().intValue()==0) {
						return ui;
					}
				}
			}
			
		}
		return null;
	}
	

	/*
	 * @Title: dealBusiness
	 * @Description: 
	 * @param @param business    参数  
	 * @author Jinlong He
	 * @param business
	 * @see com.hnjing.core.service.BMSService#dealBusiness(com.hnjing.core.model.entity.BusinessInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object dealBusiness(BusinessInfo business) {
		ClueInfo cii = new ClueInfo();
		cii.setCId(business.getClueId());
		cii.setStatuss(5);
		cii.setModifyNo(business.getGmtModifyUser());
		clueInfoService.modifyClueInfo(cii);
		
		businessInfoService.modifyBusinessInfo(business);
		return business;
	}
	
	
	
	//邮件签名
	private static String author = "<div><br></div><div><font size=\"4\">商机端口</font>&nbsp;&nbsp;业务管理部 <br>百度HI：商机管理　 | EMAIL:shangji@hnjing.com <br>-------------------------------------------------------<br>湖南竞网智赢网络技术有限公司 百度（湖南）客户服务中心<br>服务热线：400-0731-777 官网： www.hnjing.com<br>客户俱乐部：vip.hnjing.com&nbsp;&nbsp; 智营销服务平台：e.hnjing.com<br>长沙市麓谷高新区文轩路27号麓谷企业广场C3栋一层至四层（410205）<br></div>";
	
	private static String tableHeard = "<table style=\"font-family: verdana,arial,sans-serif; font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;\">";
	
	
	/** 
	* @Title: doSendSJMail 
	* @Description: 发送商机邮件
	* @param business
	* @param employee  
	* void    返回类型 
	* @throws 
	*/
	private void doSendSJMail(BusinessInfo business, Employee employee) {
		ClueInfo ci = clueInfoService.queryClueInfoByCId(business.getClueId());
		StringBuffer sb = new StringBuffer();
		sb.append(tableHeard);		
		sb.append("<tr>")
		.append(getTableTitle("商机来源", 90)).append(getTableTD(ci.getCSource()))
		.append(getTableTitle("咨询方式", 90)).append(getTableTD(ci.getCType()))
		.append("</tr>");
		
		if(ci.getEmployee()!=null && ci.getEmployee().length()>0) {
			sb.append("<tr>")
			.append(getTableTitle("提供部门")).append(getTableTD(ci.getDepartment()))
			.append(getTableTitle("提供人员")).append(getTableTD(ci.getEmployee()))
			.append("</tr>");
		}
		
		
		sb.append("<tr>")
		.append(getTableTitle("商机类型")).append(getTableTD(business.getValidInfo()))
		.append(getTableTitle("入库情况")).append(getTableTD(business.getHasIn().intValue()==0?"库内":"库外"))
		.append("</tr>");
		
		sb.append("<tr>")
		.append(getTableTitle("客户行业")).append(getTableTD(business.getIndustryJw()))
		.append(getTableTitle("归属城市")).append(getTableTD(business.getCity()))
		.append("</tr>");
		
		sb.append("<tr>")
		.append(getTableTitle("客户编码")).append(getTableTD(business.getErpId()))
		.append(getTableTitle("发送时间")).append(getTableTD(DateUtil.getDateTime()))		
		.append("</tr>");
		
		String mobile = ci.getCPhone();
		if(employee.getMailOther()!=null && employee.getMailOther().length()>0) {
			if(mobile.length()>=8) {
				mobile = (mobile.substring(0, 3)+"****"+mobile.substring(7));
			}
			if(mobile.length()>=16) {
				mobile = (mobile.substring(0, 11)+"****"+mobile.substring(15));
			}
			if(mobile.length()>=24) {
				mobile = (mobile.substring(0, 19)+"****"+mobile.substring(23));
			}
		}
		sb.append("<tr>")
		.append(getTableTitle("联系人")).append(getTableTD(ci.getCMan()))
		.append(getTableTitle("联系电话")).append(getTableTD(mobile))
		.append("</tr>");
		
		sb.append("<tr>").append(getTableTitle("企业名称")).append("<td colspan=\"3\" style=\"border-width: 1px;	padding: 8px;	border-style: solid;border-color: #666666;	background-color: #ffffff;\">"+ci.getCCustomer()+"</td></tr>");
				
		sb.append("<tr>").append(getTableTitle("产品需求")).append("<td colspan=\"3\" style=\"border-width: 1px;	padding: 8px;	border-style: solid;border-color: #666666;	background-color: #ffffff;\">"+ci.getCGoods()+"</td></tr>");
	
		sb.append("<tr>").append(getTableTitle("商机描述")).append("<td colspan=\"3\" style=\"border-width: 1px; font-size: 26px;	padding: 8px;	border-style: solid;border-color: #666666;	background-color: #ffffff;\"><font color=\"red\">"+business.getBnote()+"</font></td></tr>");
		
		sb.append("</table>");
		
		sb.append("<div>");
		sb.append("<br /><strong>祝签单顺利，大吉大利！</strong><br /><h4>	&nbsp;&nbsp;&nbsp;附：《商机跟进注意事项》</h4>");
		sb.append("&nbsp;&nbsp;&nbsp; 1、请检查客户是否分配至库内，若无特殊情况，请15分钟内联系客户，并在1小时内按模板反馈，当日有一条有效批注，如对客户信息有疑问可与商机管理员确认；<br />");
		sb.append("&nbsp;&nbsp;&nbsp; 2、商机会分配至销售库内，因权限问题未入库的，请及时处理权限问题并自行添加商机，如未及时添加入库，客户再次咨询将按公共商机处理；<br />");
		sb.append("&nbsp;&nbsp;&nbsp; 3、邮件不下发客户联系方式，请自行在ERP查看客户联系人对应的联系电话，或在商机管理-当前跟进任务-商机 中查看详细情况；<br />");
		sb.append("&nbsp;&nbsp;&nbsp; 4、【有效商机】：未按《ERP客户信息管理规则》及时跟进客户商机，会核算商机费用；<br />");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 【商机信息】：销售库内正常跟进客户，不核算商机费用；<br />");
		sb.append("&nbsp;&nbsp;&nbsp; 5、客户上线后核算商机奖励，具体核算规则参照CMP《商机管理规则》。 <br /><br />");
		sb.append("</div>");
		
		sb.append("<div>");
		sb.append(tableHeard+"<tr  width=\"900px\"><td colspan=\"3\"  style=\"align:center; text-align:center; border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;	background-color: #F1F1F1;\"><strong>商机跟进反馈邮件模板</strong></td></tr>");
		sb.append("<tr>").append(getTableTitle("具体沟通情况", 120)).append(getTableTD("&nbsp;")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("客户阶段", 120)).append(getTableTD("□达成合作意向&nbsp;&nbsp;&nbsp;&nbsp;□已约见&nbsp;&nbsp;&nbsp;&nbsp;□推迟合作&nbsp;&nbsp;&nbsp;&nbsp;□意向待促进&nbsp;&nbsp;&nbsp;&nbsp;□资质不全&nbsp;&nbsp;&nbsp;&nbsp;□网站准备中&nbsp;&nbsp;&nbsp;&nbsp;□其它________（可多选）")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("放弃跟进原因<br/>(关闭商机)", 120)).append(getTableTD("□资质因素&nbsp;&nbsp;&nbsp;&nbsp;□价格因素&nbsp;&nbsp;&nbsp;&nbsp;□未找到决策人&nbsp;&nbsp;&nbsp;&nbsp;□无意向&nbsp;&nbsp;&nbsp;&nbsp;□其它__________")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("客户其他产品需求", 120)).append(getTableTD("&nbsp;")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("客户预算", 120)).append(getTableTD("&nbsp;")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("预计成单时间", 120)).append(getTableTD("&nbsp;")).append("</tr>");
		sb.append("<tr>").append(getTableTitle("下次跟进计划", 120)).append(getTableTD("&nbsp;")).append("</tr>");
		sb.append("</table>");
		sb.append("</div>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"#ff0000\">请按规定在收到商机1个小时内按以上模板格式邮件反馈客户跟进情况，感谢您的支持！</font>");		
		
		try {				
			mailUtil.sendSimpleMail(employee.getPersonMail()+((employee.getMailOther()!=null && employee.getMailOther().length()>0)?";"+employee.getMailOther():"")+";shangji@hnjing.com",
					"【商机下发】【"+(business.getValidInfo())+"】 "+ci.getCCustomer(), 
					"<div>Dear "+employee.getPersonName()+"：</div><div>&nbsp;&nbsp;&nbsp;&nbsp; 您好！以下为本轮商机，请尽快与客户联系并按模板回复！</div><br/>"
					+sb.toString()+author);
			
		} catch (ExecutionException e) {
			//logger.error("************************************************************************邮件发送失败：");
		}	
		
		
	}
	
	/** 
	* @Title: getTableTD 
	* @Description: 组装表格单元 
	* @param text
	* @return  
	* String    返回类型 
	* @throws 
	*/
	private String getTableTD(String text) {
		return "<td style=\"border-width: 1px;	padding: 8px;	border-style: solid;border-color: #666666;	background-color: #ffffff;\" >"+text+"</td>";
	}
	
	/** 
	* @Title: getTableTitle 
	* @Description: 组装表格头单元 
	* @param titleName
	* @return  
	* String    返回类型 
	* @throws 
	*/
	private String getTableTitle(String titleName) {
		return "<td style=\"border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;	background-color: #DDF3F5;\">"+titleName+"</td>";
	}
	
	private String getTableTitle(String titleName, int width) {
		return "<td  width=\""+width+"px\" style=\"border-width: 1px;	padding: 8px;border-style: solid;border-color: #666666;	background-color: #DDF3F5;\">"+titleName+"</td>";
	}

	/*
	 * @Title: refreshToken
	 * @Description: 
	 * @param @param userInfo
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param userInfo
	 * @return
	 * @see com.hnjing.core.service.BMSService#refreshToken(com.hnjing.core.model.entity.UserInfo)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object refreshToken(UserInfo userInfo) {
		UserInfo userInfo2 = userInfoService.modifyUserToken(userInfo.getUcode());
		try {				
			mailUtil.sendSimpleMailNoRecord(userInfo.getUmail(),
					"【保密】商机管理系统授权码", 
					"<div>Dear "+userInfo.getUname()+"：</div><div>&nbsp;&nbsp;&nbsp;&nbsp; 您新的授权码为：<font color=\"red\">"+userInfo2.getToken()+"</font></div>"
					+"<div>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"http://192.168.50.175:8088/?t="+userInfo2.getToken()+"\">点击跳转</a></div>"
					+author);
			
		} catch (ExecutionException e) {
			//logger.error("************************************************************************邮件发送失败：");
		}	
		
		return 1;
	}

	/*
	 * @Title: uploadXYClue
	 * @Description: 
	 * @param @param userName
	 * @param @param xlsData
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param userName
	 * @param xlsData
	 * @return
	 * @see com.hnjing.core.service.BMSService#uploadXYClue(java.lang.String, java.util.List)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object uploadXYClue(String userName, List<List<String>> xlsData) {
		Map<String, Integer> colMap = new HashMap<String, Integer>();
		String keys = ",日期,编号,意向单ID,公司名称,联系人,联系方式,地区,咨询来源,备注,产品需求,呼通,呼出/次,线索,线索是否跟进,是否跟进成商机,线索不跟进原因,备注,客户阶段,";
		
		
		List<String> title = xlsData.get(0);
		logger.info("S:"+title.size());
		for(int i=0; i<title.size(); i++) {
			
			String t = title.get(i);
			logger.info(t);
			if(t==null || t.trim().length()==0) {
				continue;
			}
			if(keys.contains(","+t+",")) {
				colMap.put(t, i);   //包括列头
			}
		}	
		
		
		if(colMap.get("联系人")==null) {
			throw new ParameterException("联系人", "联系人列头无法匹配");
		}
		if(colMap.get("联系方式")==null) {
			throw new ParameterException("联系方式", "联系方式列头无法匹配");
		}
		int insertCount = 0;
		boolean isF = true;
		for(List<String> data : xlsData) {
			if(isF) {
				isF = false;
				continue;
			}
			if(processXYClue(userName, colMap, data)) {
				insertCount++;
			}
		}
		return insertCount+"/"+(xlsData.size()-1);
	}

	/** 
	* @Title: processXYClue 
	* @Description: 批量处理轩辕线索信息
	* @param userName
	* @param data  
	* void    返回类型 
	* @throws 
	*/
	private boolean processXYClue(String userName,Map<String, Integer> colMap, List<String> data) {
		//"日期","编号","意向单ID","公司名称","联系人","联系方式","地区","咨询来源","备注","产品需求","呼通","呼出/次","线索","线索是否跟进","是否跟进成商机","线索不跟进原因","备注","客户阶段",
		if(data!=null && data.size()>1) {
			ClueInfo ci = new ClueInfo();
			ci.setCreatedNo(userName);
			ci.setCSource("轩辕");
			ci.setCStatus("待核查");
			ci.setStatuss(0);
			ci.setCCustomer(getColValueString("公司名称", colMap, data));
			ci.setCMan(getColValueString("联系人", colMap, data));
			ci.setCPhone(getColValueString("联系方式", colMap, data));
			ci.setCCity(getColValueString("地区", colMap, data));
			ci.setCType(getColValueString("咨询来源", colMap, data));
			ci.setCMessage(getColValueString("备注", colMap, data));
			ci.setParam1(getColValueString("意向单ID", colMap, data));
			clueInfoService.addClueInfo(ci);
			return true;
		}
		return false;
	}
	


	/*
	 * @Title: uploadEjingClue
	 * @Description: TODO
	 * @param @param userName
	 * @param @param xlsData    参数  
	 * @author Jinlong He
	 * @param userName
	 * @param xlsData
	 * @see com.hnjing.core.service.BMSService#uploadEjingClue(java.lang.String, java.util.List)
	 */ 
	@Override
	@Transactional(readOnly = false)
	public Object uploadEjingClue(String userName, List<List<String>> xlsData) {
		Map<String, Integer> colMap = new HashMap<String, Integer>();
		String keys = ",日期,咨询来源,咨询方式,公司名称,联系人,联系方式,城市,备注,线索客户分类,产品分类,产品需求,咨询情况,";
		List<String> title = xlsData.get(0);
		
		for(int i=0; i<title.size(); i++) {		
			String t = title.get(i);
		
			if(t==null || t.trim().length()==0) {
				continue;
			}
			if(keys.contains(","+t+",")) {
				colMap.put(t, i);   //包括列头
			}
		}		
		if(colMap.get("公司名称")==null) {
			throw new ParameterException("公司名称", "公司名称列头无法匹配");
		}
		if(colMap.get("联系方式")==null) {
			throw new ParameterException("联系方式", "联系方式列头无法匹配");
		}
		int insertCount = 0;
		boolean isF = true;
		for(List<String> data : xlsData) {
			if(isF) {
				isF = false;
				continue;
			}
			if(processEjingClue(userName, colMap, data)) {
				insertCount++;
			}
		}
		return insertCount+"/"+(xlsData.size()-1);
		
	}

	/** 
	* @Title: processEjingClue 
	* @Description: 处理Ejing线索与商机
	* @param userName
	* @param colMap
	* @param data
	* @return  
	* boolean    返回类型 
	* @throws 
	*/
	private boolean processEjingClue(String userName, Map<String, Integer> colMap, List<String> data) {
		if(data!=null && data.size()>1) {
//			",日期,咨询来源,咨询方式,公司名称,联系人,联系方式,城市,备注,线索客户分类,产品分类,产品需求,咨询情况
			ClueInfo ci = new ClueInfo();
			ci.setCreatedNo(userName);
			ci.setModifyNo(userName);
			ci.setCSource(getColValueString("咨询来源", colMap, data));
			ci.setCStatus(getColValueString("咨询情况", colMap, data));					
			ci.setCCustomer(getColValueString("公司名称", colMap, data));
			ci.setStatuss(3); //非商机	
			//商机，且公司名称不为空
			if("商机".equals(ci.getCStatus()) && ci.getCCustomer()!=null && ci.getCCustomer().length()>3) {
				ci.setStatuss(1); //商机待发
			}
			ci.setCReason(getColValueString("线索客户分类", colMap, data));
			ci.setCMan(getColValueString("联系人", colMap, data));
			String goods = getColValueString("产品需求", colMap, data);
			if(goods!=null) {
				goods = goods.replaceAll("\\+", ",");
			}
			ci.setCGoods(goods);
			ci.setCPhone(getColValueString("联系方式", colMap, data));
			ci.setCCity(getColValueString("城市", colMap, data));
			ci.setCType(getColValueString("咨询方式", colMap, data));
			ci.setCNote(getColValueString("备注", colMap, data));
			clueInfoService.addClueInfo(ci);
			if(ci.getCId()!=null && ci.getStatuss().intValue()==1) {
				//新增商机待发
				BusinessInfo bi = new BusinessInfo();
				bi.setSjStatus(0);
				bi.setcGoods(ci.getCGoods());
				bi.setCity(ci.getCCity());
				bi.setClueId(ci.getCId());
				bi.setCCustomer(ci.getCCustomer());
				bi.setcPhone(ci.getCPhone());
				bi.setGmtCreatedUser(ci.getModifyNo());
				bi.setBnote(ci.getCNote());
				businessInfoService.addBusinessInfo(bi);
				if(bi==null || bi.getBId()==null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

//	private Integer getColValueInteger(String colName, Map<String, Integer> colMap, List<String> data) {
//	String ret = getColValueString(colName, colMap, data);
//	if (ret != null) {
//		try {
//			return Integer.parseInt(ret);
//		} catch (Exception e) {
//
//		}
//	}
//	return null;
//}
	
	private String getColValueString(String colName, Map<String, Integer> colMap, List<String> data) {
		if(colMap.containsKey(colName) && colMap.get(colName)!=null && data!=null) {
			if(colMap.get(colName).intValue()<data.size()) {
				String ret =  data.get(colMap.get(colName));
				if(ret!=null) {
					return ret.trim().replaceAll("'", "").replaceAll("\"", "");
				}
			}
		}		
		return null;		
	}
	

}
