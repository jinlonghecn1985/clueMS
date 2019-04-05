package com.hnjing.utils;

import java.io.Serializable;

public class HttpEntityVo implements Serializable {
	
	private Integer code;
	
	private String entity;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		return "HttpEntityVo [code=" + code + ", entity=" + entity + "]";
	}

}
