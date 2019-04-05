package com.hnjing.config.web.filter;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import io.swagger.models.auth.In;


@Aspect   //定义一个切面
@Configuration
public class LogRecordAspect {
private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.hnjing.*.controller.*Controller.*(..))")
    public void excudeService() {
    }

//    @Around("execution(* com.hnjing.*.controller.*Controller.*(..))")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//        
//        
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        String str, wholeStr = "";
//        if(request.getContentLength()>0){
//        	 BufferedReader br = request.getReader();
//    	     while((str = br.readLine()) != null){
//    	     wholeStr += str;
//    	     }
//        }
//       
//        Enumeration<String> e = request.getHeaderNames();
//        String queryHeader = "";
//        while (e.hasMoreElements()) {
//        	String hn = e.nextElement();
//        	queryHeader += ("\""+hn+"\":\""+request.getHeader(hn)+"\",");            
//        }
//		if(queryHeader!=null && queryHeader.length()>0){
//			queryHeader = queryHeader.substring(0, queryHeader.length()-1);
//		}
//        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}, body:{}, header:{}", url, method, uri, queryString,wholeStr, queryHeader);
//        //20180118
//        //增加过滤器（拦截器）处理所有接口：
//        //1、从header中取emp-ids：员工id列表
//        //2、从query中取curEmpId：当前操作员工id
//        //判断curEmpId是否在emp-ids中，如果不在，则返回403：无权限操作
//        String curEmpId = request.getParameter("curEmpId");
//        String empIds = request.getHeader("emp-ids");
//        boolean isAuth = false;
//        if(curEmpId!=null && curEmpId.length()>0 && empIds!=null && empIds.length()!=0){
//        	String[] ids = empIds.split(",");
//        	for(int i=0; i<ids.length; i++){
//        		if(empIds.equals(ids[i])){
//        			isAuth = true;
//        			break;
//        		}
//        	}
//        }
//        //TODO 开发暂时关闭
//        isAuth = false;
//        if(isAuth){
//        	Map<String, Object> ret = new HashMap<String, Object>();
//        	ret.put("code", 403);
//        	ret.put("message", "无权限操作");
//        	sra.getResponse().setStatus(403);
//        	return ret;
//        }
//        //end 20180118
//        // result的值就是被拦截方法的返回值
//        Object result = pjp.proceed();
//        JSONObject json = new JSONObject();
//        logger.info("请求结束，controller的返回值是 " + json.toJSONString(result));
//        return result;
//    }
}
