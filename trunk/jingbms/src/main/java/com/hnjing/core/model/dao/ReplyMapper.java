package com.hnjing.core.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnjing.utils.paginator.domain.PageBounds;
import com.hnjing.core.model.entity.Reply;

/**
 * @ClassName: ReplyMapper
 * @Description: 销售回复映射
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 17时06分
 */
@Mapper
public interface ReplyMapper {

	/**
	 * @Title: addReply
	 * @Description:添加销售回复
	 * @param reply 实体
	 * @return Integer
	 */
	Integer addReply(Reply reply);
	
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
	 * @param pageBounds 分页信息
	 * @param reply 实体
	 * @return List<Reply>
	 */
	List<Reply> queryReplyForPage(PageBounds pageBounds, @Param("reply") Reply reply);
	 
	 /**
	  * @Title: queryReplyByProperty
	  * @Description:根据属性查询销售回复
	  * @return List<Reply>
	  */
	 List<Reply> queryReplyByProperty(@Param("reply") Map<String, Object> map);
	 
	 
	 
}
