/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: DZFP.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.utils.httpclient
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年1月12日 下午3:33:31
 * @version: V1.0  
 */
package com.hnjing.utils.httpclient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.apache.http.util.EntityUtils;

import com.hnjing.utils.StringUtils;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * @ClassName: DZFP
 * @Description: 电子发票获取PDF测试类
 * @author: Jinlong He
 * @date: 2019年1月12日 下午3:33:31
 */
public class DZFP {

	public static void main(String []arg) {
	
		String res = "http://pis.baiwang.com/invoice?param=E349C2C6E34B31A656E731B1BEF09EBA46532A457BA33B31B7BEC13C652651CE";
		String code = res.split("=")[1];
		
		HttpClientResult r = sendRequest(HttpClientMethod.POST, "http://pis.baiwang.com/bwmg/mix/bw/previewInvoice", code, null, true, null);
		String ids = r.getBody().substring(r.getBody().indexOf("\"invoiceID\":\"")+13);
		ids = ids.substring(0, ids.indexOf("\""));
		
		HttpClientResult r2 = sendRequest(HttpClientMethod.GET, "http://pis.baiwang.com/bwmg/mix/bw/getPdfUrl?invoiceId="+ids, null, null, false, null);
		
		ids = r2.getBody().substring(r2.getBody().indexOf("\"data\":[\"")+9);
		ids = ids.substring(0, ids.indexOf("\""));//		System.out.println(ids);
		HttpClientResult r3 = sendRequest(HttpClientMethod.GET, ids, null, null, false, code);
		System.out.println("文件保存"+r3.getBody());
		System.out.println("发票内容*****************************");
		System.out.println(readPdf(r3.getBody()));
		
		
//		String res2 = "https://inv.jss.com.cn/group4/M00/04/04/wKj6yFukUc6IbwRqAACTzbqtrSYAAAz7AISzqEAAJPl234.pdf";
//		String code2 = res2.split("eid=")[1];
//		System.out.println(code2);
//		HttpClientResult r4 = sendRequest(HttpClientMethod.GET, res2, null, null, false, code2);
//		System.out.println(readPdf(r4.getBody()));
	}
	
	
	public static String readPdf(String fileName){
		String pageContent = "";
		try {
			PdfReader reader = new PdfReader(fileName);
			int pageNum = reader.getNumberOfPages();
			for(int i=1;i<=pageNum;i++){
				pageContent += PdfTextExtractor.getTextFromPage(reader, i);//读取第i页的文档内容
			}
//			writer.write(pageContent);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//			writer.close();
		}
		return pageContent;
	}
	
	
	
	public static HttpClientResult sendRequest(HttpClientMethod httpMethod, String url, String parasMap,
			Map<String, String> headers, boolean isRAW, String encoding) {
		DZFP hcu = new DZFP();
		System.out.println(url);
//		if(parasMap!=null && httpMethod.getCode()==HttpClientMethod.GET.getCode()){
//			//GET请求拼装数据到URL
//			if(url.indexOf("?")==-1){
//				url +="?";
//			}
//			for(String key : parasMap.keySet()){
//				Object kv = parasMap.get(key);
//				if(kv instanceof String){
//					url+=("&"+key+"="+(String)kv);
//				}else if(kv instanceof Integer){
//					url+=("&"+key+"="+(Integer)kv);
//				}else{
//					//其它情况
//					//url+=("&"+key+"="+kv.toString());
//				}
//			}
//			url= url.replace("?&", "?");
//			
//		}
		// 创建请求对象
		HttpRequestBase request = hcu.getRequest(url, httpMethod);
		
		// 设置header信息
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				request.setHeader(key, headers.get(key));
			}
		}
//		if (parasMap == null) {
//			parasMap = new HashMap<String, Object>();
//		}
//		parasMap.putAll(hcu.getParamsFromURL(url)); // 参数合并
		//url = hcu.removeParamsFromURL(url); // 净化请求地址 --特殊情况，不能净化参数
//		logger.info("请求:"+url +"入参："+ JsonUtil.map2json(parasMap));
		// 判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
		if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
			try {
				if (isRAW) {
					StringEntity postingString = new StringEntity(parasMap, "utf-8");// json字符串传递
					((HttpEntityEnclosingRequestBase) request).setEntity(postingString);// 设置参数到请求对象中
				} else {
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//					for (String key : parasMap.keySet()) {
//						nvps.add(new BasicNameValuePair(key, (String) parasMap.get(key)));
//					}
					// 设置参数到请求对象中
					((HttpEntityEnclosingRequestBase) request).setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

				}
			} catch (UnsupportedEncodingException e) {
				return new HttpClientResult(500,
						"{\"code\":500,\"error\":\"set Entity ("+"utf-8"+") error:" + e.getMessage() + "\"}");
			}
		}
		// 调用发送请求
		return hcu.execute(HttpClients.createDefault(), request, url, encoding);

	}
	
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
					if(encoding!=null && encoding.length()>0) {
//						System.out.println(StringUtils.byteArray2String(b, " "));
						 FileOutputStream os = new FileOutputStream(encoding+".pdf");  
						             // 写入输出流  
						              os.write(b);  
						              // 关闭输出流  
						            os.close(); 
						          
						 return new HttpClientResult("D:\\workspace-sts\\SiteMonitor\\trunk\\jingsm\\"+encoding+".pdf");
					}
					 
					String c ="";
					boolean lm = true;					
					if(lm) {
						c = new String(b, "utf-8");					
						lm = false;
						//System.out.println("utf:"+lm+t);
					}	
					System.out.println("*************************");
				
				
					
						ret = new HttpClientResult(c);
					
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

}
