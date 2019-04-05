/**  
 * Copyright © 2018公司名字. All rights reserved.
 * @Title: HttpToolUtil.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.ws.service.impl.util
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2018年11月17日 下午4:31:03
 * @version: V1.0  
 */
package com.hnjing.utils.httpclient;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: HttpToolUtil
 * @Description: 网站相关工具类
 * @author: Jinlong He
 * @date: 2018年11月17日 下午4:31:03
 */
public class HttpToolUtil {

	public static String regex = "^((https|http|ftp|rtsp|mms)?://)"
			+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9%a-z][0-9%a-z-]{0,61})?[0-9%a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn't required if there is no file name
			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
	
	public static String IPregex = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))"; //IP
	
	public static String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
	public static String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
	public static String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式
	
	public static Map<Integer, String> errorMap = new HashMap<Integer, String>() {		
		/**
		 * @fieldName: serialVersionUID
		 * @fieldType: long
		 * @Description: 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(100, "请求者应当继续提出请求");
			put(101, "切换协议");
			put(200, "成功");
			put(201, "已创建");
			put(202, "已接受");
			put(203, "非授权信息");
			put(204, "无内容");
			put(205, "重置内容");
			put(206, "部分内容");
			put(300, "多种选择");
			put(301, "永久移动");
			put(302, "临时移动");
			put(303, "查看其他位置");
			put(304, "未修改");
			put(305, "使用代理");
			put(307, "临时重定向");
			put(400, "错误请求");
			put(401, "未授权");
			put(403, "禁止");
			put(404, "请求的网页不存在");
			put(405, "方法禁用");
			put(406, "不接受");
			put(407, "需要代理授权");
			put(408, "请求超时");
			put(409, "冲突");
			put(410, "已删除");
			put(411, "需要有效长度");
			put(412, "未满足前提条件");
			put(413, "请求实体过大");
			put(414, "请求的URI过长");
			put(415, "不支持的媒体类型");
			put(416, "请求范围不符合要求");
			put(417, "未满足期望值");
			put(500, "服务器内部错误");
			put(501, "尚未实施");
			put(502, "错误网关");
			put(503, "服务不可用");
			put(504, "网关超时(6秒+无法加载)");
			put(505, "HTTP版本不受支持");
		}
	};
	
	/** 
	* @Title: isURL 
	* @Description: 是否标准URL地址
	* @param url 
	* @return  
	* boolean    返回类型 
	* @throws 
	*/
	public static boolean isURL(String url){
        //转换为小写
		url = url.toLowerCase();
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
//		return true;
    }
	
	
	/** 
	* @Title: delHTMLTag 
	* @Description: 去掉HTML标签 包括脚本、样式、标签
	* @param htmlStr
	* @return  
	* String    返回类型 
	* @throws 
	*/
	public static String delHTMLTag(String htmlStr){          
       Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
       Matcher m_script=p_script.matcher(htmlStr); 
       htmlStr=m_script.replaceAll(""); //过滤script标签 
        
       Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
       Matcher m_style=p_style.matcher(htmlStr); 
       htmlStr=m_style.replaceAll(""); //过滤style标签 
        
       Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
       Matcher m_html=p_html.matcher(htmlStr); 
       htmlStr=m_html.replaceAll(""); //过滤html标签       
       return htmlStr.trim(); //返回文本字符串 
   } 
	
	/** 
	* @Title: getDemin 
	* @Description: 获取域名
	* @param url
	* @return  
	* String    返回类型 
	* @throws 
	*/
	public static String getDemin(String url) {
		String demin = url;
		if(url.contains("/")) {
			//包含
			String[] u = url.split("/");
			for(String c : u) {
				if(c.contains(".") && c.length()>3) {
					demin = c; 
					break;
				}
			}
		}
		return demin;
	}
	
	public static String getUrl(String page) {
		if(page==null) {
			return null;
		}
		page = page.trim();
		if(page.endsWith("/")) {
			page = page.substring(0, page.length()-1);
		}
		if(!page.toLowerCase().startsWith("http")) {
			page = "http://"+page;
		}
		//System.out.println(page.indexOf("."));
		if(page.indexOf(".", page.indexOf(".")+1)==-1) {
			page = page.replace("://", "://www.");
		}
		//System.out.println(page.indexOf(".", page.indexOf(".")+1));
		return page;
	}
}
