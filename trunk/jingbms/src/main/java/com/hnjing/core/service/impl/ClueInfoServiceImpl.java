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

import com.hnjing.core.model.dao.ClueInfoMapper;
import com.hnjing.core.model.entity.ClueInfo;
import com.hnjing.core.service.ClueInfoService;
import com.hnjing.core.service.impl.bo.ClueRepeatBo;
import com.hnjing.utils.Constant;
import com.hnjing.utils.DateUtil;
import com.hnjing.utils.file.office.ExcelWriteUtil;
import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.utils.paginator.domain.PageList;
import com.hnjing.utils.paginator.domain.PageService;

/**
 * @ClassName: ClueInfo
 * @Description: 线索信息服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 16时56分
 */
@Service("clueInfoService")
@Transactional(readOnly=true)
public class  ClueInfoServiceImpl implements ClueInfoService {	
	private static final Logger logger = LoggerFactory.getLogger(ClueInfoServiceImpl.class);
	
	@Autowired
    private ClueInfoMapper clueInfoMapper;  
	    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addClueInfo
	 * @Description:添加线索信息
	 * @param clueInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public ClueInfo addClueInfo(ClueInfo clueInfo){
		int ret = clueInfoMapper.addClueInfo(clueInfo);
		if(ret>0){
			return clueInfo;
		}
		return null;
	}
	
	/**
	 * @Title modifyClueInfo
	 * @Description:修改线索信息
	 * @param clueInfo 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyClueInfo(ClueInfo clueInfo){
		return clueInfoMapper.modifyClueInfo(clueInfo);
	}
	
	/**
	 * @Title: dropClueInfoByCId
	 * @Description:删除线索信息
	 * @param cId 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropClueInfoByCId(Integer cId){
		return clueInfoMapper.dropClueInfoByCId(cId);
	}
	
	/**
	 * @Title: queryClueInfoByCId
	 * @Description:根据实体标识查询线索信息
	 * @param cId 实体标识
	 * @return ClueInfo
	 */
	@Override
	public ClueInfo queryClueInfoByCId(Integer cId){
		return clueInfoMapper.queryClueInfoByCId(cId);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryClueInfoForPage
	 * @Description: 根据线索信息属性与分页信息分页查询线索信息信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param clueInfo 实体
	 * @return List<ClueInfo>
	 */
	@Override
	public Map<String, Object> queryClueInfoForPage(Integer pagenum, Integer pagesize, String sort, ClueInfo clueInfo){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, ClueInfo.class);
		List<ClueInfo> entityList = clueInfoMapper.queryClueInfoForPage(pageBounds, clueInfo);
		if(entityList!=null && entityList.size()>0) {
			for(ClueInfo ci : entityList) {
				if(ci!=null && ci.getCPhone()!=null && ci.getCPhone().length()>7) {
					ci.setCPhone(ci.getCPhone().substring(0, 3)+"****"+ci.getCPhone().substring(ci.getCPhone().length()-4));
				}
			}
		}
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, ClueInfo.class);
		}
		
		PageList<ClueInfo> pagelist = (PageList<ClueInfo>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryClueInfoByProperty
	 * @Description:根据属性查询线索信息
	 * @return List<ClueInfo>
	 */
	@Override
	public List<ClueInfo> queryClueInfoByProperty(Map<String, Object> map){
		return clueInfoMapper.queryClueInfoByProperty(map);
	}

	/*
	 * @Title: exportByProperty
	 * @Description: TODO
	 * @param @param map
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param map
	 * @return
	 * @see com.hnjing.core.service.ClueInfoService#exportByProperty(java.util.Map)
	 */ 
	@Override
	public HSSFWorkbook exportByProperty(Map<String, Object> map) {
		String[] title = {"线索标识 ", "来源", "状态", "城市", "企业名称", "联系人", "联系电话", "咨询方式", "线索判定", "判定补充", "提供部门", "提供人员", "产品意向", "沟通留言", "客户留言", "备参1", "备参2", "新增人员", "创建时间", "修订人员", "修订时间"};
		String[][] data = null;
		List<ClueInfo> info = queryClueInfoByProperty(map);
		if(info!=null && info.size()>0) {
			data = new String[info.size()][title.length];
			for(int j=0; j<info.size(); j++) {
				ClueInfo ci = info.get(j);
				data[j][0] = ""+ci.getCId();
				data[j][1] = ci.getCSource();
				data[j][2] = getClueStatusCN(ci.getStatuss());				
				data[j][3] = ci.getCCity();
				data[j][4] = ci.getCCustomer();		
				data[j][5] = ci.getCMan();					
				data[j][6] = ci.getCPhone();
				data[j][7] = ci.getCType();
				data[j][8] = ci.getCStatus();
				data[j][9] = ci.getCReason();
				
				data[j][10] = ci.getDepartment();
				data[j][11] = ci.getEmployee();
				data[j][12] = ci.getCGoods();
				data[j][13] = ci.getCNote();
				data[j][14] = ci.getCMessage();
				data[j][15] = ci.getParam1();
				data[j][16] = ci.getParam2();
				data[j][17] = ci.getCreatedNo();
				data[j][18] = info.get(j).getGmtCreated()!=null?DateUtil.DateToString(ci.getGmtCreated()):"";
				data[j][19] = ci.getModifyNo();				
				data[j][20] = info.get(j).getGmtModify()!=null?DateUtil.DateToString(ci.getGmtModify()):"";
			}
		}
		return ExcelWriteUtil.getHSSFWorkbook("线索详情", title, data, null);
	}
	
	private String getClueStatusCN(Integer status) {
		if(status==null) {
			return "--";
		}
		if(status==0) {
			return "待核查";
		}else if(status==1) {
			return "商机待发";
		}else if(status==2) {
			return "商机跟进";
		}else if(status==3) {
			return "非商机";
		}else if(status==4) {
			return "商机中止";
		}else if(status==5) {
			return "商机成交";
		}
		return "未定义";
	}

	/*
	 * @Title: queryClueInfoRepeatForPage
	 * @Description: 
	 * @param @param pagenum
	 * @param @param pagesize
	 * @param @param sort
	 * @param @param clueInfo
	 * @param @return    参数  
	 * @author Jinlong He
	 * @param pagenum
	 * @param pagesize
	 * @param sort
	 * @param clueInfo
	 * @return
	 * @see com.hnjing.core.service.ClueInfoService#queryClueInfoRepeatForPage(java.lang.Integer, java.lang.Integer, java.lang.String, com.hnjing.core.model.entity.ClueInfo)
	 */ 
	@Override
	public Map<String, Object> queryClueInfoRepeatForPage(Integer pagenum, Integer pagesize, String sort,
			ClueInfo clueInfo) {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, ClueInfo.class);
		List<ClueInfo> entityList = clueInfoMapper.queryClueInfoForPage(pageBounds, clueInfo);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, ClueInfo.class);
		}
		
		PageList<ClueInfo> pagelist = (PageList<ClueInfo>) entityList;
		List<ClueRepeatBo> dataList = new ArrayList<ClueRepeatBo>();
		if(entityList!=null && entityList.size()>0) {
			for(ClueInfo ci : entityList) {
				ClueRepeatBo cib = new ClueRepeatBo();
				cib.setClueInfo(ci);
				Map<String, Object> countMap = clueInfoMapper.queryClueRepeatCount(ci.getCId(), ci.getCCustomer()==null?"hejinlong@hnjing.com":ci.getCCustomer(), ci.getCPhone()==null?"hejinlong@hnjing.com":ci.getCPhone());
				cib.setRepeatNameCount(((Long)countMap.get("nameRepeat")).intValue());
				cib.setRepeatPhoneCount(((Long)countMap.get("phoneRepeat")).intValue());
				if(ci!=null && ci.getCPhone()!=null && ci.getCPhone().length()>7) {
					ci.setCPhone(ci.getCPhone().substring(0, 3)+"****"+ci.getCPhone().substring(ci.getCPhone().length()-4));
				}
				dataList.add(cib);
			}
		}
		returnMap.put(Constant.PAGELIST, dataList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}


}
