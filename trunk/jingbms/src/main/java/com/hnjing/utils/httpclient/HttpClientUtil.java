package com.hnjing.utils.httpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnjing.utils.JsonUtil;
import com.hnjing.utils.StringUtil;
import com.hnjing.utils.StringUtils;

/**
 * @ClassName: HttpClientUtil
 * @Description: 此类主要模拟访问，返回去掉HTML代码的内容
 * @author: Jinlong He
 * @date: 2019年1月8日 上午11:21:28
 */
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	/**
	 * 请求资源或服务，自定义client对象，传入请求参数， 设置内容类型，并指定参数和返回数据的编码
	 * 
	 * @param httpMethod
	 *            请求方法
	 * @param url
	 *            资源地址
	 * @param parasMap
	 *            请求参数
	 * @param headers
	 *            请求头信息
	 * @param isRAW
	 *            参数以raw形式传送
	 * @param encoding
	 *            编码
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	public static HttpClientResult sendRequest(HttpClientMethod httpMethod, String url, Map<String, Object> parasMap,
			Map<String, String> headers, boolean isRAW, String encoding) {
		HttpClientUtil hcu = new HttpClientUtil();

		if(parasMap!=null && httpMethod.getCode()==HttpClientMethod.GET.getCode()){
			//GET请求拼装数据到URL
			if(url.indexOf("?")==-1){
				url +="?";
			}
			for(String key : parasMap.keySet()){
				Object kv = parasMap.get(key);
				if(kv instanceof String){
					url+=("&"+key+"="+(String)kv);
				}else if(kv instanceof Integer){
					url+=("&"+key+"="+(Integer)kv);
				}else{
					//其它情况
					//url+=("&"+key+"="+kv.toString());
				}
			}
			url= url.replace("?&", "?");
			
		}
		// 创建请求对象
		HttpRequestBase request = hcu.getRequest(url, httpMethod);
		
		// 设置header信息
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				request.setHeader(key, headers.get(key));
			}
		}
		if (parasMap == null) {
			parasMap = new HashMap<String, Object>();
		}
		parasMap.putAll(hcu.getParamsFromURL(url)); // 参数合并
		//url = hcu.removeParamsFromURL(url); // 净化请求地址 --特殊情况，不能净化参数
		logger.info("请求:"+url +"入参："+ JsonUtil.map2json(parasMap));
		// 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
		if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
			try {
				if (isRAW) {
					StringEntity postingString = new StringEntity(JsonUtil.map2json(parasMap), encoding);// json字符串传递
					((HttpEntityEnclosingRequestBase) request).setEntity(postingString);// 设置参数到请求对象中
				} else {
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					for (String key : parasMap.keySet()) {
						nvps.add(new BasicNameValuePair(key, (String) parasMap.get(key)));
					}
					// 设置参数到请求对象中
					((HttpEntityEnclosingRequestBase) request).setEntity(new UrlEncodedFormEntity(nvps, encoding));

				}
			} catch (UnsupportedEncodingException e) {
				return new HttpClientResult(500,
						"{\"code\":500,\"error\":\"set Entity ("+encoding+") error:" + e.getMessage() + "\"}");
			}
		}
		// 调用发送请求
		return hcu.execute(HttpClients.createDefault(), request, url, encoding);

	}
	
	public static HttpClientResult sendRequest(HttpClientMethod httpMethod, String url, String params,
			Map<String, String> headers, String encoding) {
		HttpClientUtil hcu = new HttpClientUtil();			
		// 创建请求对象
		HttpRequestBase request = hcu.getRequest(url, httpMethod);
		// 设置header信息
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				request.setHeader(key, headers.get(key));
			}
		}
		url = hcu.removeParamsFromURL(url); // 净化请求地址
		logger.info("请求:"+url +"入参："+ JsonUtil.object2json(params));
		// 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
		if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {			
			StringEntity postingString = new StringEntity(params, encoding);// json字符串传递
					((HttpEntityEnclosingRequestBase) request).setEntity(postingString);// 设置参数到请求对象中				
			
		}
		// 调用发送请求
		return hcu.execute(HttpClients.createDefault(), request, url, encoding);

	}
	
	public String getIP(String domain) {
		String ipAddress = "";
		InetAddress iAddress = null;
		try {
			iAddress = InetAddress.getByName(domain);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (iAddress == null)
			logger.info(domain, "iAddress ==null");
		else {
			ipAddress = iAddress.getHostAddress();
		}
		return ipAddress;
	}

//	private static String asciiToNative(String asciicode) {
//		String[] asciis = asciicode.split("\\\\u");
//		String nativeValue = asciis[0];
//		try {
//			for (int i = 1; i < asciis.length; i++) {
//				String code = asciis[i];
//				nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
//				if (code.length() > 4) {
//					nativeValue += code.substring(4, code.length());
//				}
//			}
//		} catch (NumberFormatException e) {
//			return asciicode;
//		}
//		return nativeValue;
//	}
	
    public static String unicodeToString(String str) {
    	 
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
 
        Matcher matcher = pattern.matcher(str);
 
        char ch;
 
        while (matcher.find()) {
 
            ch = (char) Integer.parseInt(matcher.group(2), 16);
 
            str = str.replace(matcher.group(1), ch + "");    
 
        }
 
        return str;
 
    }

	/**
	 * 请求资源或服务
	 * 
	 * @param client
	 *            client对象
	 * @param request
	 *            请求对象
	 * @param url
	 *            资源地址
	 * @param parasMap
	 *            请求参数
	 * @param encoding
	 *            编码
	 * @return 返回处理结果
	 * @throws HttpProcessException
	 */
	private HttpClientResult execute(HttpClient client, HttpRequestBase request, String url, String encoding) {
		//设置超时
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
				.setConnectionRequestTimeout(3000)
				.setSocketTimeout(5000).build();
		request.setConfig(requestConfig);
        
		HttpClientResult ret = new HttpClientResult();
	//	String ip = getIpAddress(request);
		HttpResponse response = null;
		try {
			// 执行请求操作，并拿到结果（同步阻塞）
			response = client.execute(request);
//			Header[] a = response.getAllHeaders();
//			for(Header h : a) {
//				System.out.println(h.getName()+" "+ h.getValue());
//				if(h.getElements()!=null) {
//					for( HeaderElement e : h.getElements()) {
//						System.out.println("   "+e.getName()+" "+e.getValue());
//						if(e.getParameters()!=null) {
//							for( NameValuePair p : e.getParameters()) {
//								System.out.println("        "+p.getName()+" "+p.getValue());
//							}
//						}
//					}
//				}
//			}
			
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {					
				try {
					byte b[] = EntityUtils.toByteArray(entity); // 所有的内容都读到此数组之中	
					System.out.println(StringUtils.byteArray2String(b, " "));
					 FileOutputStream os = new FileOutputStream("fp23.pdf");  
					             // 写入输出流  
					              os.write(b);  
					              // 关闭输出流  
					            os.close();  
					String c ="";
					String t = "";
					boolean lm = true;					
					if(lm) {
						c = (new String(b, "utf-8")).toLowerCase().replaceAll("\r|\n", "");						
						t = StringUtil.getStringFromContent(c, "<title>", "</title>");						
						lm = !isUTF8(b);
						//System.out.println("utf:"+lm+t);
					}	
					
//					if(lm) {
//						c = (new String(b, "gb2312")).toLowerCase().replaceAll("\r|\n", "");
//						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
////						lm = true;
//						lm = isMessyCode(t);
//						//System.out.println("gb2312"+lm+t);
//					}
//					
					if(lm) {
						c = (new String(b, "gbk")).toLowerCase().replaceAll("\r|\n", "");
						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
						if(c.contains("charset=gb2312") || c.contains("charset=gbk")) {
							lm = false;
						}else {
							lm = isMessyCode(t);
						}
						//System.out.println("gbk:"+lm+t);
					}
					if(lm) {						
						c = StringEscapeUtils.unescapeHtml3(new String(b)).toLowerCase().replaceAll("\r|\n", "");	//十进制	Unicode				
						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
						if(t!=null) {
							t=t.replaceAll("\\\\", "");
						}
						if(c.contains("charset=unicode")) {
							lm = false;
						}else {
							lm = isMessyCode(t);
						}
						//System.out.println("Unicode"+lm+t);
					}
					
					if(lm) {
						c = (new String(b, "iso-8859-1")).toLowerCase().replaceAll("\r|\n", "");
						t = StringUtil.getStringFromContent(c, "<title>", "</title>");
						lm = isMessyCode(t);
					}
					System.out.println(t);
					System.out.println(c);
					System.out.println("utf:"+c);
					if(!lm) {
						ret = new HttpClientResult(t);
						if(t==null || t.length()==0) {
							if(c.contains("window.location")) {
								ret = new HttpClientResult(c);
							}else if(c.contains("location.href")) {
								ret = new HttpClientResult(c);
							}
						}
					}else {
						ret = new HttpClientResult(c);
					}
//					System.out.println("BODY:"+ret.getBody());
				} catch (UnsupportedEncodingException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}				
			}
			ret.setCode(response.getStatusLine().getStatusCode()); // 设置状态
			EntityUtils.consume(entity);
		} catch (IOException e) {
			ret.setCode(500);
			ret.setBody("{\"code\":500,\"error\":\"execute http client error:" + e.getMessage() + "\"}");
		} finally {
			closeResponse(response);
		}
		//logger.info("返回："+ret.getBody());
		return ret;
	}

	/**
	 * 关闭response
	 * 
	 * @param resp
	 *            HttpResponse对象
	 */
	private void closeResponse(HttpResponse resp) {
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

	/**
	 * 根据请求方法名，获取request对象
	 * 
	 * @param url
	 *            资源地址
	 * @param method
	 *            请求方式
	 * @return
	 */
	private HttpRequestBase getRequest(String url, HttpClientMethod method) {
		HttpRequestBase request = null;
		switch (method.getCode()) {
		case 0:// HttpGet
			request = new HttpGet(url);
			break;
		case 1:// HttpPost
			request = new HttpPost(url);
			break;
		case 2:// HttpHead
			request = new HttpHead(url);
			break;
		case 3:// HttpPut
			request = new HttpPut(url);
			break;
		case 4:// HttpDelete
			request = new HttpDelete(url);
			break;
		case 5:// HttpTrace
			request = new HttpTrace(url);
			break;
		case 6:// HttpPatch
			request = new HttpPatch(url);
			break;
		case 7:// HttpOptions
			request = new HttpOptions(url);
			break;
		default:
			request = new HttpPost(url);
			break;
		}
		return request;
	}

	/**
	 * @Title: removeParamsFromURL
	 * @Description: 去掉路径中的参数
	 * @param url
	 * @return String 返回类型
	 */
	private String removeParamsFromURL(String url) {
		if (url != null && url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			return url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	/**
	 * 获取路径中的参数
	 * 
	 * @param url
	 *            资源地址
	 * @return List<NameValuePair>
	 */
	private Map<String, String> getParamsFromURL(String url) {
		Map<String, String> ret = new HashMap<String, String>();
		if (url != null && url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			// 不空，包括?，?号在=前
			String param = url.substring(url.indexOf("?") + 1); // 参数
			if (param != null && param.length() > 0) {
				String[] p = param.split("&"); // 拆开
				for (int i = 0; i < p.length; i++) {
					if (p[i] == null || p[i].length() < 3 || !p[i].contains("=")) {
						continue;
					}
					String[] kv = p[i].split("=");
					if (kv[0] != null && kv[0].length() > 0 && kv[1] != null && kv[1].length() > 0) {
						ret.put(kv[0], kv[1]);
						// new BasicNameValuePair(kv[0], kv[1])
					}
				}
			}
		}
		return ret;
	}
	
	
	 public static boolean isMessyCode(String strName) {
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
	    
	    public static boolean isChinese(char c) {
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
	    
	public static boolean isUTF8(byte[] rawtext) {
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
	
//	
//    public static String getIpAddress(HttpServletRequest request) {  
//        String ip = request.getHeader("x-forwarded-for");  
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//            ip = request.getHeader("Proxy-Client-IP");  
//        }  
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//            ip = request.getHeader("WL-Proxy-Client-IP");  
//        }  
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//            ip = request.getHeader("HTTP_CLIENT_IP");  
//        }  
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
//        }  
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
//            ip = request.getRemoteAddr();  
//        }  
//        return ip;  
//    } 
}
