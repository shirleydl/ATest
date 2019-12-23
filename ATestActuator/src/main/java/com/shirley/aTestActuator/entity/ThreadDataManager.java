package com.shirley.aTestActuator.entity;

import java.util.Map;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年12月4日 上午9:25:15
*/
public class ThreadDataManager {
	private boolean callback=false;
	private Map<String, String> bindMapAll;

	public boolean isCallback() {
		return callback;
	}
	public void setCallback(boolean callback) {
		this.callback = callback;
	}
	public Map<String, String> getBindMapAll() {
		return bindMapAll;
	}
	public void setBindMapAll(Map<String, String> bindMapAll) {
		this.bindMapAll = bindMapAll;
	}

	
	
}
