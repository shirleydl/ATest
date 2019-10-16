package com.shirley.aTestActuator.entity;

import java.util.Map;

/**
 * @Description: TODO(替换类)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午9:08:39
 */
public class Replace {
	private Map<String,String> replaceUrl;
	private Map<String,Map<String,String>> replaceData;
	private String split;

	public Map<String,String> getReplaceUrl() {
		return replaceUrl;
	}

	public void setReplaceUrl(Map<String,String> replaceUrl) {
		this.replaceUrl = replaceUrl;
	}

	public Map<String,Map<String,String>> getReplaceData() {
		return replaceData;
	}

	public void setReplaceData(Map<String,Map<String,String>> replaceData) {
		this.replaceData = replaceData;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	
	

}
