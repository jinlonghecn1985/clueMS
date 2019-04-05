/**  
 * Copyright © 2018公司名字. All rights reserved.
 * @Title: HttpClientSiteUtil.java
 * @Prject: WebHealthMonitor
 * @Package: com.hnjing.utils.httpclient
 * @Description: 
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2018年10月17日 上午10:41:42
 * @version: V1.0  
 */
package com.hnjing.utils.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: HttpClientSiteUtil
 * @Description: 网站检测专用定制
 * @author: Jinlong He
 * @date: 2018年10月17日 上午10:41:42
 */
public class HttpClientSiteUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientSiteUtil.class);

	private static RequestConfig shortRequestConfig = RequestConfig.custom().setConnectTimeout(4000)
			.setConnectionRequestTimeout(3000).setSocketTimeout(4000).build();

	private static RequestConfig longRequestConfig = RequestConfig.custom().setConnectTimeout(10000)
			.setConnectionRequestTimeout(9000).setSocketTimeout(10000).build();

	private static HttpClient client = HttpClients.createDefault();

	/** 
	* @Title: getDeminTitle 
	* @Description: 检测域名并返回网站标题
	* @param url 待检测域名
	* @return  
	* HttpClientResult    返回类型 
	* @throws 
	*/
	public static HttpClientResult getDeminTitle(String url,  String charset) {
		HttpClientResult r = getDeminTitle(url, charset, false, false); //快速 初级检测
		if(r==null || r.getCode()==null || r.getCode().intValue()!=200) {
			r = getDeminTitle(url, charset, true, false); //慢速 初级检测
			if(r!=null && r.getCode()!=null && r.getCode()==200) {
				r.setFastSlow(3);//3秒以上
				logger.info("httpclient status: *slow* "+r.getCode()+" 域名:"+url);
				return r;
			}
			r = getDeminTitle(url, charset, true, true); //慢速 安全检测
			if(r!=null && r.getCode()!=null && r.getCode()==200) {
				r.setFastSlow(6);//安全处理			
			}
			logger.info("httpclient status: *SAFE* "+r.getCode()+" 域名:"+url);
			return r;
		}
		logger.info("httpclient status:"+r.getCode()+" 域名:"+url);
		return r;
	}
	
	
	/** 
	* @Title: getDeminTitle 
	* @Description: 检测域名并返回网站标题
	* @param url 待检测域名
	* @param isLongTimeOut 是否应用长时间超时
	* @return  
	* HttpClientResult    返回类型 
	* @throws 
	*/
	private static HttpClientResult getDeminTitle(String url, String charset, boolean isLongTimeOut, boolean addHeader) {		
		url = IDN.toASCII(url);  //解决中文域名
		if(!url.contains("://")) {
			url = "http://"+url;
		}
		 
		HttpRequestBase request = new HttpGet(url);		
		if(addHeader) {
			request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
			request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			request.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
			request.addHeader("Accept-Encoding", "gzip, deflate");
			request.addHeader("Connection", "keep-alive");
			request.addHeader("Upgrade-Insecure-Requests", "1");
		}
		if (isLongTimeOut) {
			//穿部分防火墙
			request.setConfig(longRequestConfig);			
		} else {
			request.setConfig(shortRequestConfig);
		}
				
		HttpClientResult ret = new HttpClientResult();
		HttpResponse response = null;
		try {
			// 执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(request);
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					byte b[] = EntityUtils.toByteArray(entity); // 所有的内容都读到此数组之中
					if(charset!=null && charset.length()>0) {
						return new HttpClientResult((new String(b, charset)).toLowerCase().replaceAll("\r|\n", ""));
					}
					
					
					String c = "";
					boolean lm = true;
					if (lm) {
						c = (new String(b, "utf-8")).toLowerCase().replaceAll("\r|\n", "");
						lm = !isUTF8(b);
					}

					if (lm) {
						c = (new String(b, "gb2312")).toLowerCase().replaceAll("\r|\n", "");
						lm = isMessyCode(c);
					}

					if (lm) {
						c = (new String(b, "gbk")).toLowerCase().replaceAll("\r|\n", "");						
						lm = isMessyCode(c);
					}
					//处理unicode
//					if (lm) {
//						c = StringEscapeUtils.unescapeHtml3(new String(b)).toLowerCase().replaceAll("\r|\n", ""); // 十进制
//																													// Unicode
////						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
////						if (t != null) {
////							t = t.replaceAll("\\\\", "");
////						}
//						lm = isMessyCode(c);
//						// System.out.println("Unicode"+lm+t);
//					}

//					if (lm) {
//						c = (new String(b, "iso-8859-1")).toLowerCase().replaceAll("\r|\n", "");
//						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
//						lm = isMessyCode(t);
//					}
					// System.out.println(t);
					// System.out.println(c);
					ret = new HttpClientResult(c);
					// System.out.println("BODY:"+ret.getBody());
				} catch (UnsupportedEncodingException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
			}
			ret.setCode(response.getStatusLine().getStatusCode()); // 设置状态
			EntityUtils.consume(entity);
		} catch (IOException e) {
			ret.setCode(504);
			ret.setBody("{\"code\":504,\"error\":\"execute http client error:" + e.getMessage() + "\"}");
		} finally {
			closeResponse(response);
		}
		
		return ret;
	}
	
	private static void closeResponse(HttpResponse resp) {
		if (resp == null) {
			return;
		}
		try {
			// 如果CloseableHttpResponse 是resp的父类，则支持关闭
			if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
				((CloseableHttpResponse) resp).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private static boolean isUTF8(byte[] rawtext) {
		int score = 0;
		int i, rawtextlen = 0;
		int goodbytes = 0, asciibytes = 0;
		// Maybe also use UTF8 Byte Order Mark: EF BB BF
		// Check to see if characters fit into acceptable ranges
		rawtextlen = rawtext.length;
		for (i = 0; i < rawtextlen; i++) {
			if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {
				// 最高位是0的ASCII字符
				asciibytes++;
				// Ignore ASCII, can throw off count
			} else if (-64 <= rawtext[i] && rawtext[i] <= -33
			// -0x40~-0x21
					&& // Two bytes
					i + 1 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65) {
				goodbytes += 2;
				i++;
			} else if (-32 <= rawtext[i] && rawtext[i] <= -17 && // Three bytes
					i + 2 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2]
					&& rawtext[i + 2] <= -65) {
				goodbytes += 3;
				i += 2;
			}
		}
		if (asciibytes == rawtextlen) {
			return false;
		}
		score = 100 * goodbytes / (rawtextlen - asciibytes);
		// If not above 98, reduce to zero to prevent coincidental matches
		// Allows for some (few) bad formed sequences
		if (score > 98) {
			return true;
		} else if (score > 95 && goodbytes > 30) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|t*|r*|n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}

	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

}
