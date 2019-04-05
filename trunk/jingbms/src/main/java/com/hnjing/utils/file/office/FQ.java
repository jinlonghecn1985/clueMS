/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: FQ.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.utils.file.office
 * @Description: 
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年1月17日 上午9:48:49
 * @version: V1.0  
 */
package com.hnjing.utils.file.office;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FQ
 * @Description: 
 * @author: Jinlong He
 * @date: 2019年1月17日 上午9:48:49
 */
public class FQ {
	private String question;
	private Map<String, FQDetail> answer;
	private static String lineSeparator = System.getProperty("line.separator");
	
	private Integer yesCount=0;
	private Integer noCount=0;
	private Integer allCount=0;
	
	public FQ(String question) {
		this.question = question;
		answer = new HashMap<String, FQDetail>();
	}
	
	/** 
	* @Title: setAnswer 
	* @Description: 添加回答及计数
	* @param ans
	* @return  
	* Integer    返回类型 
	* @throws 
	*/
	public Integer setAnswer(String ans) {
		if(ans==null || ans.length()==0) {
			return 0;
		}
		ans = ans.trim();
		if(answer==null) {
			answer = new HashMap<String, FQDetail>();
		}
		if(!answer.containsKey(ans)) {
			answer.put(ans, new FQDetail());			
		}
		allCount++;
		answer.get(ans).setCounts(answer.get(ans).getCounts()+1); //数量加1
		return answer.get(ans).getCounts();
	}
	
	/** 
	* @Title: setAnswerType 
	* @Description: 设置回答类型
	* @param key
	* @param type  
	* void    返回类型 
	* @throws 
	*/
	public void setAnswerType(String key, Integer type) {
		if(answer.containsKey(key)) {
			answer.get(key).setTypes(type);	
			if(type.intValue()==1) {
				yesCount +=(answer.get(key).getCounts());
			}else if(type.intValue()==2) {
				noCount +=(answer.get(key).getCounts());
			}
		}
	}
	
//	public Integer get
	
	public String answerToString() {
//		if(answer==null || answer.size()==0) {
//			return "null:null";
//		}	
		return "all:"+allCount+",yes:"+yesCount+",no:"+noCount+",other:"+(allCount-yesCount-noCount);
	}
	
	public String answerDetailToString() {
		if(answer==null || answer.size()==0) {
			return "null:null";
		}
		StringBuffer sb = new StringBuffer();
		int i=0;
		for(String a:answer.keySet()) {
			String at = answer.get(a).getTypes().intValue()==1?"YES ":(answer.get(a).getTypes().intValue()==2?"NO ":"OTH ");
			sb.append("    "+(++i)+" ").append(a).append(":").append(at).append(answer.get(a).getWord()+" ").append(answer.get(a).getCounts()).append(lineSeparator);
		}
		String ret = sb.toString();
		return ret.substring(0, ret.length()-1);
	}
	
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the answer
	 */
	public Map<String, FQDetail> getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(Map<String, FQDetail> answer) {
		this.answer = answer;
	}

	/**
	 * @return the yesCount
	 */
	public Integer getYesCount() {
		return yesCount;
	}

	/**
	 * @param yesCount the yesCount to set
	 */
	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	/**
	 * @return the noCount
	 */
	public Integer getNoCount() {
		return noCount;
	}

	/**
	 * @param noCount the noCount to set
	 */
	public void setNoCount(Integer noCount) {
		this.noCount = noCount;
	}

	/**
	 * @return the otherCount
	 */
	public Integer getAllCount() {
		return allCount;
	}

	/**
	 * @param otherCount the otherCount to set
	 */
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}
}
