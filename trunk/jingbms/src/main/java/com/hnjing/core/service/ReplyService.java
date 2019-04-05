package com.hnjing.core.service;

import java.util.List;
import java.util.Map;


import com.hnjing.core.model.entity.Reply;

/**
 * @ClassName: Reply
 * @Description: 销售回复服务接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 17时06分
 */
public interface ReplyService {

    /**
	 * @Title: addReply
	 * @Description:添加销售回复
	 * @param reply 实体
	 * @return Integer
	 */
	Reply addReply(Reply reply);
	
	/**
	 * @Title modifyReply
	 * @Description:修改销售回复
	 * @param reply 实体
	 * @return Integer
	 */
	Integer modifyReply(Reply reply);
	
	/**
	 * @Title: dropReplyById
	 * @Description:删除销售回复
	 * @param id 实体标识
	 * @return Integer
	 */
	Integer dropReplyById(Integer id);
	
	/**
	 * @Title: queryReplyById
	 * @Description:根据实体标识查询销售回复
	 * @param id 实体标识
	 * @return Reply
	 */
	Reply queryReplyById(Integer id);
	 
	/**
	 * @Title: queryReplyForPage
	 * @Description: 根据销售回复属性与分页信息分页查询销售回复信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param reply 实体
	 * @return List<Reply>
	 */
	Map<String, Object> queryReplyForPage(Integer pagenum, Integer pagesize, String sort, Reply reply);
	 
	 /**
	 * @Title: queryReplyByProperty
	 * @Description:根据属性查询销售回复
	 * @return List<Reply>
	 */
	 List<Reply> queryReplyByProperty(Map<String, Object> map);	 
	 
}
