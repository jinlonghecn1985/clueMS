package com.hnjing.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnjing.utils.Constant;
import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.utils.paginator.domain.PageList;
import com.hnjing.utils.paginator.domain.PageService;


import com.hnjing.core.model.entity.Reply;
import com.hnjing.core.model.dao.ReplyMapper;
import com.hnjing.core.service.ReplyService;

/**
 * @ClassName: Reply
 * @Description: 销售回复服务实现类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 17时06分
 */
@Service("replyService")
@Transactional(readOnly=true)
public class  ReplyServiceImpl implements ReplyService {	
	private static final Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);
	
	@Autowired
    private ReplyMapper replyMapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: addReply
	 * @Description:添加销售回复
	 * @param reply 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Reply addReply(Reply reply){
		int ret = replyMapper.addReply(reply);
		if(ret>0){
			return reply;
		}
		return null;
	}
	
	/**
	 * @Title modifyReply
	 * @Description:修改销售回复
	 * @param reply 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modifyReply(Reply reply){
		return replyMapper.modifyReply(reply);
	}
	
	/**
	 * @Title: dropReplyById
	 * @Description:删除销售回复
	 * @param id 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer dropReplyById(Integer id){
		return replyMapper.dropReplyById(id);
	}
	
	/**
	 * @Title: queryReplyById
	 * @Description:根据实体标识查询销售回复
	 * @param id 实体标识
	 * @return Reply
	 */
	@Override
	public Reply queryReplyById(Integer id){
		return replyMapper.queryReplyById(id);
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: queryReplyForPage
	 * @Description: 根据销售回复属性与分页信息分页查询销售回复信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param reply 实体
	 * @return List<Reply>
	 */
	@Override
	public Map<String, Object> queryReplyForPage(Integer pagenum, Integer pagesize, String sort, Reply reply){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, Reply.class);
		List<Reply> entityList = replyMapper.queryReplyForPage(pageBounds, reply);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, Reply.class);
		}
		
		PageList<Reply> pagelist = (PageList<Reply>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 
	/**
	 * @Title: queryReplyByProperty
	 * @Description:根据属性查询销售回复
	 * @return List<Reply>
	 */
	@Override
	public List<Reply> queryReplyByProperty(Map<String, Object> map){
		return replyMapper.queryReplyByProperty(map);
	}


}
