package com.shirley.aTest.entity;

import java.util.Map;

/**
 * @Description: TODO(测试集-接口用例)
 * @author 371683941@qq.com
 * @date 2019年6月27日 下午5:06:34
 */
public class TestSuiteWithCase implements Comparable<TestSuiteWithCase> {
	private int id;
	private int testSuiteId;
	private int interfaceCaseId;
	private String interfaceCaseName;
	private String interfaceApi;
	private int priority;
	private int timeout;
	private int redirect;
	private int retry;
	private int interval;
	private int delay;
	private Map<String, String> bindVariables;
	private String caseVariablesSplit;
	private Map<String, String> caseVariables;

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

	public int getInterfaceCaseId() {
		return interfaceCaseId;
	}

	public void setInterfaceCaseId(int interfaceCaseId) {
		this.interfaceCaseId = interfaceCaseId;
	}

	public String getInterfaceCaseName() {
		return interfaceCaseName;
	}

	public void setInterfaceCaseName(String interfaceCaseName) {
		this.interfaceCaseName = interfaceCaseName;
	}

	public String getInterfaceApi() {
		return interfaceApi;
	}

	public void setInterfaceApi(String interfaceApi) {
		this.interfaceApi = interfaceApi;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRedirect() {
		return redirect;
	}

	public void setRedirect(int redirect) {
		this.redirect = redirect;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public Map<String, String> getBindVariables() {
		return bindVariables;
	}

	public void setBindVariables(Map<String, String> bindVariables) {
		this.bindVariables = bindVariables;
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

	@Override
	public int compareTo(TestSuiteWithCase testSuiteWithCase) {
		// TODO Auto-generated method stub
		return this.getPriority() - testSuiteWithCase.getPriority();
	}

}
