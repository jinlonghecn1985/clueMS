/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: FQDetail.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.utils.file.office
 * @Description: 
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年1月17日 下午12:33:51
 * @version: V1.0  
 */
package com.hnjing.utils.file.office;

/**
 * @ClassName: FQDetail
 * @Description: 
 * @author: Jinlong He
 * @date: 2019年1月17日 下午12:33:51
 */
public class FQDetail {
	private Integer types = 3; //1是 2否 3其它
	private Integer counts = 0; //数量
	private String word;
	/**
	 * @return the types
	 */
	public Integer getTypes() {
		return types;
	}
	/**
	 * @param types the types to set
	 */
	public void setTypes(Integer types) {
		this.types = types;
	}
	/**
	 * @return the counts
	 */
	public Integer getCounts() {
		return counts;
	}
	/**
	 * @param counts the counts to set
	 */
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

}
