package com.shirley.aTest.entity;

import java.util.Map;

/**
 * @Description: TODO(替换类)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午9:08:39
 */
public class Replace {
	private int id;
	private String name;
	private String description;
	private Map<String,String> replaceUrl;
	private Map<String,Object> replaceData;
	private String split;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String,String> getReplaceUrl() {
		return replaceUrl;
	}

	public void setReplaceUrl(Map<String,String> replaceUrl) {
		this.replaceUrl = replaceUrl;
	}

	public Map<String,Object> getReplaceData() {
		return replaceData;
	}

	public void setReplaceData(Map<String,Object> replaceData) {
		this.replaceData = replaceData;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}
	
	

	

}
