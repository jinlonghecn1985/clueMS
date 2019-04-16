package com.hnjing.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hnjing.core.model.dao.BusinessInfoMapper;
import com.hnjing.core.model.entity.BusinessInfo;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.service.BusinessInfoService;
import com.hnjing.core.service.impl.bo.BusinessInfoBo;
import com.hnjing.core.service.impl.bo.BusinessRepeatBo;
import com.hnjing.core.service.impl.bo.ClueRepeatBo;
import com.hnjing.utils.Constant;
import com.hnjing.utils.DateUtil;
import com.hnjing.utils.file.office.ExcelWriteUtil;
import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.utils.paginator.domain.PageList;
import com.hnjing.utils.paginator.domain.PageService;

/**
 * @ClassName: BusinessInfo
 * @Description: 商机信息服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月20日 14时16分
 */
@Service("businessInfoService")
@Transactional(readOnly=true)
public class  BusinessInfoServiceImpl implements BusinessInfoService {	
	private static final Logger logger = LoggerFactory.getLogger(BusinessInfoServiceImpl.class);
	
	@Autowired
    private BusinessInfoMapper businessInfoMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addBusinessInfo
	 * @Description:添加商机信息
	 * @param businessInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public BusinessInfo addBusinessInfo(BusinessInfo businessInfo){
		int ret = businessInfoMapper.addBusinessInfo(businessInfo);
		if(ret>0){
			return businessInfo;
		}
		return null;
	}
	
	/**
	 * @Title modifyBusinessInfo
	 * @Description:修改商机信息
	 * @param businessInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyBusinessInfo(BusinessInfo businessInfo){
		return businessInfoMapper.modifyBusinessInfo(businessInfo);
	}
	
	/**
	 * @Title: dropBusinessInfoByBId
	 * @Description:删除商机信息
	 * @param bId 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropBusinessInfoByBId(Integer bId){
		return businessInfoMapper.dropBusinessInfoByBId(bId);
	}
	
	/**
	 * @Title: queryBusinessInfoByBId
	 * @Description:根据实体标识查询商机信息
	 * @param bId 实体标识
	 * @return BusinessInfo
	 */
	@Override
	public BusinessInfo queryBusinessInfoByBId(Integer bId){
		return businessInfoMapper.queryBusinessInfoByBId(bId);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryBusinessInfoForPage
	 * @Description: 根据商机信息属性与分页信息分页查询商机信息信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param businessInfo 实体
	 * @return List<BusinessInfo>
	 */
	@Override
	public Map<String, Object> queryBusinessInfoForPage(Integer pagenum, Integer pagesize, String sort, BusinessInfo businessInfo){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, BusinessInfo.class);
		List<BusinessInfo> entityList = businessInfoMapper.queryBusinessInfoForPage(pageBounds, businessInfo);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, BusinessInfo.class);
		}
		
		PageList<BusinessInfo> pagelist = (PageList<BusinessInfo>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryBusinessInfoByProperty
	 * @Description:根据属性查询商机信息
	 * @return List<BusinessInfo>
	 */
	@Override
	public List<BusinessInfo> queryBusinessInfoByProperty(Map<String, Object> map){
		return businessInfoMapper.queryBusinessInfoByProperty(map);
	}

	/*
	 * @Title: exportByProperty
	 * @Description: 
	 * @param @param map
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param map
	 * @return
	 * @see com.hnjing.core.service.BusinessInfoService#exportByProperty(java.util.Map)
	 */ 
	@Override
	public HSSFWorkbook exportByProperty(Map<String, Object> map) {
		String[] title = {"商机标识 ", "ERP标识", "商机来源", "咨询方式", "商机类型", "补充说明", "竞网行业", "商机状态", "归属城市", "企业名称", "联系人", "联系电话", 
				"是否入库", "是否新客", "受理部门", "受理人员", "产品意向", "成交产品", "提交部门", "提交人员", "商机留言", "沟通备注", "新增人员", "创建时间", "修订人员", 
				"修订时间"};
		String[][] data = null;
		List<BusinessInfoBo> info = queryBusinessInfoBoByProperty(map);
		if(info!=null && info.size()>0) {
			data = new String[info.size()][title.length];
			for(int j=0; j<info.size(); j++) {
				BusinessInfoBo ci = info.get(j);
				data[j][0] = ""+ci.getBId();
				data[j][1] = ""+ci.getErpId();
				data[j][2] = ci.getCSource();
				data[j][3] = ci.getCType();
				data[j][4] = ci.getValidInfo();	
				data[j][5] = ci.getCReason();
				data[j][6] = ci.getIndustryJw();
				data[j][7] = getSJStatusCN(ci.getSjStatus());	
				data[j][8] = ci.getCity();			
				data[j][9] = ci.getCCustomer();
				
				data[j][10] = ci.getcMan();
				data[j][11] = ci.getcPhone();
				data[j][12] = ci.getHasIn()==null?"":(ci.getHasIn().intValue()==0?"库内":"库外");
				data[j][13] = ci.getIsNew()==null?"":(ci.getIsNew()==0?"新客":"老客");
				data[j][14] = ci.getDepartment();
				data[j][15] = ci.getEmployee();
				data[j][16] = ci.getcGoods();
				data[j][17] = ci.getBusiness();
				data[j][18] = ci.getFromDepartment();
				data[j][19] = ci.getFromEmployee();
				
				data[j][20] = ci.getBnote();
				data[j][21] = ci.getCNote();
				data[j][22] = ci.getGmtCreatedUser();
				data[j][23] = info.get(j).getGmtCreated()!=null?DateUtil.DateToString(ci.getGmtCreated()):"";
				data[j][24] = ci.getGmtModifyUser();		
				data[j][25] = info.get(j).getGmtModify()!=null?DateUtil.DateToString(ci.getGmtModify()):"";
			}
		}
		return ExcelWriteUtil.getHSSFWorkbook("商机详情", title, data, null);
	}
	
	private String getSJStatusCN(Integer status) {
		if(status==null) {
			return "--";
		}
		if(status==0) {
			return "待分配";
		}else if(status==1) {
			return "跟进中";
		}else if(status==7) {
			return "已关闭";
		}else if(status==8) {
			return "已合作";
		}
		return "未定义";
	}

	/*
	 * @Title: queryBusinessInfoBoByProperty
	 * @Description: TODO
	 * @param @param map
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param map
	 * @return
	 * @see com.hnjing.core.service.BusinessInfoService#queryBusinessInfoBoByProperty(java.util.Map)
	 */ 
	@Override
	public List<BusinessInfoBo> queryBusinessInfoBoByProperty(Map<String, Object> map) {
		return businessInfoMapper.queryBusinessInfoBoByProperty(map);
	}

	/*
	 * @Title: queryBusinessInfoRepeatForPage
	 * @Description: TODO
	 * @param @param pagenum
	 * @param @param pagesize
	 * @param @param sort
	 * @param @param businessInfo
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param pagenum
	 * @param pagesize
	 * @param sort
	 * @param businessInfo
	 * @return
	 * @see com.hnjing.core.service.BusinessInfoService#queryBusinessInfoRepeatForPage(java.lang.Integer, java.lang.Integer, java.lang.String, com.hnjing.core.model.entity.BusinessInfo)
	 */ 
	@Override
	public Map<String, Object> queryBusinessInfoRepeatForPage(Integer pagenum, Integer pagesize, String sort,
			BusinessInfo businessInfo) {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, BusinessInfo.class);
		List<BusinessInfo> entityList = businessInfoMapper.queryBusinessInfoForPage(pageBounds, businessInfo);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, BusinessInfo.class);
		}		
		PageList<BusinessInfo> pagelist = (PageList<BusinessInfo>) entityList;
		List<BusinessRepeatBo> dataList = new ArrayList<BusinessRepeatBo>();
		if(entityList!=null && entityList.size()>0) {
			for(BusinessInfo bi : entityList) {
				BusinessRepeatBo bib = new BusinessRepeatBo();
				bib.setBusinessInfo(bi);
				Map<String, Object> countMap = businessInfoMapper.queryBusinessRepeatCount(bi.getBId(), bi.getCCustomer()==null?"hejinlong@hnjing.com":bi.getCCustomer(), bi.getcPhone()==null?"hejinlong@hnjing.com":bi.getcPhone());
				bib.setRepeatNameCount(((Long)countMap.get("nameRepeat")).intValue());
				bib.setRepeatPhoneCount(((Long)countMap.get("phoneRepeat")).intValue());
				if(bi!=null && bi.getcPhone()!=null && bi.getcPhone().length()>7) {
					bi.setcPhone(bi.getcPhone().substring(0, 3)+"****"+bi.getcPhone().substring(bi.getcPhone().length()-4));
				}
				dataList.add(bib);
			}
		}
		
		returnMap.put(Constant.PAGELIST, dataList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}

	/*
	 * @Title: queryBusinessInfoBySaleToken
	 * @Description: TODO
	 * @param @param saleToken
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param saleToken
	 * @return
	 * @see com.hnjing.core.service.BusinessInfoService#queryBusinessInfoBySaleToken(java.lang.String)
	 */ 
	@Override
	public BusinessInfo queryBusinessInfoBySaleToken(String saleToken) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("saletoken", saleToken);
		List<BusinessInfoBo> rets = queryBusinessInfoBoByProperty(query);
		if(rets!=null && rets.size()>0) {
			return rets.get(0);
		}
		return null;
	}


}
