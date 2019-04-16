package com.hnjing.core.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hnjing.config.validation.BeanValidator;
import com.hnjing.config.web.exception.AuthorityException;
import com.hnjing.config.web.exception.NotFoundException;
import com.hnjing.config.web.exception.ParameterException;
import com.hnjing.core.controller.bo.UserInfoBo;
import com.hnjing.core.model.entity.Reply;
import com.hnjing.core.service.BMSService;
import com.hnjing.core.service.ReplyService;
import com.hnjing.utils.ClassUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: ReplyController
 * @Description: 销售回复HTTP接口
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2019年03月19日 17时06分
 */
@RestController
@Api(description="销售回复")
public class ReplyController{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private BMSService bmsService;

	
	@ApiOperation(value = "新增 添加销售回复信息", notes = "添加销售回复信息")
	@RequestMapping(value = "/reply", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object addReply(HttpServletResponse response,
			@ApiParam(value = "reply") @RequestBody Reply reply) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		reply.setId(null);
		replyService.addReply(reply);
		return reply;
	}
	
	
	@ApiOperation(value = "更新 根据销售回复标识更新销售回复信息", notes = "根据销售回复标识更新销售回复信息")
	@RequestMapping(value = "/reply/{id:.+}", method = RequestMethod.PUT)
	public Object modifyReplyById(HttpServletResponse response,
			@PathVariable Integer id,
			@ApiParam(value = "reply", required = true) @RequestBody Reply reply
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Reply tempReply = replyService.queryReplyById(id);
		reply.setId(id);
		if(null == tempReply){
			throw new NotFoundException("销售回复");
		}
		return replyService.modifyReply(reply);
	}

	@ApiOperation(value = "删除 根据销售回复标识删除销售回复信息", notes = "根据销售回复标识删除销售回复信息")
	@RequestMapping(value = "/reply/{id:.+}", method = RequestMethod.DELETE)
	public Object dropReplyById(HttpServletResponse response, @PathVariable Integer id) {
		Reply reply = replyService.queryReplyById(id);
		if(null == reply){
			throw new NotFoundException("销售回复");
		}
		return replyService.dropReplyById(id);
	}
	
	@ApiOperation(value = "查询 根据销售回复标识查询销售回复信息", notes = "根据销售回复标识查询销售回复信息")
	@RequestMapping(value = "/reply/{id:.+}", method = RequestMethod.GET)
	public Object queryReplyById(HttpServletResponse response,
			@PathVariable Integer id) {
		Reply reply = replyService.queryReplyById(id);
		if(null == reply){
			throw new NotFoundException("销售回复");
		}
		return reply;
	}
	
	@ApiOperation(value = "查询 根据销售回复属性查询销售回复信息列表", notes = "根据销售回复属性查询销售回复信息列表")
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public Object queryReplyList(HttpServletResponse response, 
			Reply reply) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {	
//		UserInfoBo ui = new UserInfoBo(bmsService.getUserInfo(request));
//		if(ui.getUserInfo()==null) {
//			throw new AuthorityException("用户权限错误");
//		}
		if(reply==null || reply.getBId()==null) {
			throw new ParameterException("bId", "商机标识必传！");
		}
		System.out.println("bid:"+reply.getBId()+""+ ClassUtil.transBean2Map(reply, false));
		return replyService.queryReplyByProperty(ClassUtil.transBean2Map(reply, false));
	}
	
	@ApiOperation(value = "查询分页 根据销售回复属性分页查询销售回复信息列表", notes = "根据销售回复属性分页查询销售回复信息列表")
	@RequestMapping(value = "/replys", method = RequestMethod.GET)
	public Object queryReplyPage(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, Reply reply) {				
		return replyService.queryReplyForPage(pagenum, pagesize, sort, reply);
	}

}
