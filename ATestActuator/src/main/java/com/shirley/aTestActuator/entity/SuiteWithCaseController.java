package com.shirley.aTestActuator.entity;

import java.util.List;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月9日 上午11:40:07
*/
public class SuiteWithCaseController implements Comparable<SuiteWithCaseController>  {
	private int id;
	private int testSuiteId;
	private String controllerType;
	private int startPriority;
	private int endPriority;
	private int priority;
	private int loopCount;
	private List<Asserts> asserts;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTestSuiteId() {
		return testSuiteId;
	}
	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	public String getControllerType() {
		return controllerType;
	}
	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}
	public int getStartPriority() {
		return startPriority;
	}
	public void setStartPriority(int startPriority) {
		this.startPriority = startPriority;
	}
	public int getEndPriority() {
		return endPriority;
	}
	public void setEndPriority(int endPriority) {
		this.endPriority = endPriority;
	}
	
	public int getLoopCount() {
		return loopCount;
	}
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}
	
	public List<Asserts> getAsserts() {
		return asserts;
	}
	public void setAsserts(List<Asserts> asserts) {
		this.asserts = asserts;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public int compareTo(SuiteWithCaseController suiteWithCaseController) {
		// TODO Auto-generated method stub
		if(this.getStartPriority()==suiteWithCaseController.getPriority())
			return this.getPriority() - suiteWithCaseController.getPriority();
		else
			return this.getStartPriority()-suiteWithCaseController.getStartPriority();
	}
	
	

}
