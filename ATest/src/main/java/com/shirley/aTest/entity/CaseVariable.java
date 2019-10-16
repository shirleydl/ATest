package com.shirley.aTest.entity;

import java.util.Map;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月10日 下午2:41:38
*/
public class CaseVariable {
	private int testSuiteWithCaseId;
	private String caseVariablesSplit;
	private Map<String, String> caseVariables;
	
	public int getTestSuiteWithCaseId() {
		return testSuiteWithCaseId;
	}
	public void setTestSuiteWithCaseId(int testSuiteWithCaseId) {
		this.testSuiteWithCaseId = testSuiteWithCaseId;
	}
	public String getCaseVariablesSplit() {
		return caseVariablesSplit;
	}
	public void setCaseVariablesSplit(String caseVariablesSplit) {
		this.caseVariablesSplit = caseVariablesSplit;
	}
	public Map<String, String> getCaseVariables() {
		return caseVariables;
	}
	public void setCaseVariables(Map<String, String> caseVariables) {
		this.caseVariables = caseVariables;
	}
	
	
	

}
