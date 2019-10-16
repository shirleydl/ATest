package com.shirley.aTest.entity;

import java.util.List;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月12日 上午11:57:51
*/
public class DoIndex {
	private int requestIndex;
	private int controllerIndex;
	private List<AssertResult> assertResultList;
	public int getRequestIndex() {
		return requestIndex;
	}
	public void setRequestIndex(int requestIndex) {
		this.requestIndex = requestIndex;
	}
	public int getControllerIndex() {
		return controllerIndex;
	}
	public void setControllerIndex(int controllerIndex) {
		this.controllerIndex = controllerIndex;
	}
	public List<AssertResult> getAssertResultList() {
		return assertResultList;
	}
	public void setAssertResultList(List<AssertResult> assertResultList) {
		this.assertResultList = assertResultList;
	}
	

}
