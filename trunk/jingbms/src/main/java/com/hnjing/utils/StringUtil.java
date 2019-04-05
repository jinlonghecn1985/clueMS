package com.hnjing.utils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @ClassName: StringUtil  
 * @Description: 字符串工具类（需要逐步完善）
 * @author li chao
 * @date 2016年3月15日 下午4:31:34   @version V1.0  
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

	/**
	 * 半角转全角
	 * 
	 * @param input
	 *            String.
	 * @return 全角字符串.
	 */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}
		String returnString = new String(c);

		return returnString;
	}

	public static String getStringFromContent(String content, String start, String end) {
		content = content.replaceAll("\r\n","\n");
		start = start.replaceAll("\r\n","\n");
		end = end.replaceAll("\r\n","\n");
		Pattern pattern = Pattern.compile(start,Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		boolean rs = matcher.find();
		//没有找到
		if(!rs)
			return "";
		int startIndex = matcher.end();
		
		pattern = Pattern.compile(end,Pattern.DOTALL);
		matcher = pattern.matcher(content);
		rs = matcher.find(startIndex);
		//没有找到
		if(!rs)
			return "";
		int endIndex = matcher.start();
		return content.substring(startIndex,endIndex);
	}
	 
	/**
	 * @Title: stringReplace  
	 * @Description: 正则表达式替换字符串 
	 * @param string
	 *            源字符串
	 * @param regEx
	 *            正则表达式
	 * @param replacement
	 *            新内容
	 * @return String    返回新字符串  
	 */
	public static String stringReplace(String string, String regEx, String replacement) {
		return string.replaceAll(regEx, replacement);
	}

	/**
	 * @Title: clearHTMLToString  
	 * @Description: 清理html
	 * @return String  返回新字符串
	 * @author li chao
	 */
	public static String clearHTMLToString(String str) {
		if (StringUtils.isEmpty(str))
			return "";
		str = StringEscapeUtils.escapeHtml4(str);
		return str;
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * @Title: isInteger  
	 * @Description: 判断是否为整数
	 * @return boolean    返回类型  
	 * @author li chao @throws  
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * @Title: random6String  
	 * @Description: 生成6位数字字符串 
	 * @return String    随机的六位数字字符串"\d{6}"  
	 * @author li chao
	 */
	@SuppressWarnings("rawtypes")
	public static String getRandom6String() {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(2, 8);
		return result;
	}

	/**
	 * @Title: entityToString  
	 * @Description:Entity对象toString()的通用处理,用json方式显示各属性值 
	 * @param obj
	 * @return String    返回对象转成json后的字符串 
	 * @author liwenchao
	 */
	public static String entityToString(Object obj) {
		SerializerFeature[] fmtConfig = new SerializerFeature[] { SerializerFeature.PrettyFormat, // 格式化排版
				SerializerFeature.UseSingleQuotes, // 使用单引号
				SerializerFeature.SortField, // 排序
				SerializerFeature.WriteDateUseDateFormat }; // 日期格式yyyy-MM-dd
															// HH:mm:ss
		String str = MessageFormat.format("{0}:{1}", obj.getClass().getSimpleName(), JSON.toJSONString(obj, fmtConfig));
		return str;
	}

	/**
	 * 
	 * @Title: toLowerCaseFirstOne @Description: 首字母转小写 @param s @return String
	 * 返回类型 @throws
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 
	 * @Title: toUpperCaseFirstOne @Description: 首字母转大写 @param s @return String
	 * 返回类型 @throws
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
//	public static void main(String[] arg) {
//		String a = "岳阳巴陵升华电器设备有限公司_岳阳透明表箱|不锈钢|表箱外壳      	              	联系我们加入收藏?-设为首页?-                    	    	                      经验丰富、技术力量雄厚、工艺装备完善                专业生产的高低压电器成套设备                                                                                                                                	                    网站首页                          关于我们                                  产品展示                                  新闻资讯                                  案例展示                                  售后服务                                  联系我们                    	                                                                                                                                                	  						关键词：透明表箱|                不锈钢|                表箱外壳|  产品四|                产品五 -->          								24小时咨询服务热线13077119181			                                    产品展示            product                                                    透明表箱                      不锈钢系列                      表箱外壳                                                                    	产品展示/product          	+more                                                                            ct预付费                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                  单相16表                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                  d型透明表箱                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                  三相6表位                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                  三相单表位                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                  单相单表位                   岳阳巴陵升华电器设备有限公司专业生产的高低压电器成...                 查看详情                                                                                                  		        		                                                	                关于我们 & about                  公司专业生产高低压电器成套设备                    	                        　　岳阳巴陵升华电器设备有限公司座落在美丽富饶的洞庭湖畔，风景秀丽的旅游城市中心，榜依千古岳阳楼，地处长江黄金水路、京广铁路、武广高铁、京珠高速公路、随岳高速、107国道的交汇点，将企业迈上新的台阶，以崭新的面貌服务于社会。本公司专业生产的高低压电器成套设备，现已拥有中国质量...                          						                        查看更多                                                                                     			    	    	          	新闻资讯/news          	+more                                                                                                                                   电表箱使用注意的地方               1.安装时请仔细检查电表箱各零部件是否完好。2.电表箱应垂直安装。倾斜角度不得超过54°。3.安装位置应选择无剧烈振动、冲击及不足以腐蚀电器元件的地方。4.因电...                  2018-09-27                 查看详情                                                                                                   	                                                              	● 电表箱有哪些优良性能2018-09-27                                                             	● 恭喜巴陵升华电器网站上线！2018-09-27                                                             	● 配电箱要做好哪些防火准备2018-09-27                                                             	● 照明配电箱的工作原理介绍2018-09-27                                                                                                                   	联系我们/contact                                  		拥有一批高素质的技术设计人员深受广大客户的喜爱       地址：               岳阳市岳阳楼区琵琶王路50号        立即咨询                             13077119181                            		              	                      售后服务：      	13077119181 -->               -->                  			关于我们          		产品展示          		新闻资讯          		案例展示          		售后服务          		联系我们                          	电话：13077119181地址：岳阳市岳阳楼区琵琶王路50号                      	  		版权所有：岳阳巴陵升华电器设备有限公司  湘icp备18019470号-1  技术支持：竞网智赢营业执照查阅   	  	        在线qq	     -->        	                    咨询热线            13077119181";
//		a = a.replaceAll("\\s{1,}", " ");
//		System.out.println(a);
//		a = a.replaceAll("\t", " ");
//		System.out.println(a);
//		
//	}

}
