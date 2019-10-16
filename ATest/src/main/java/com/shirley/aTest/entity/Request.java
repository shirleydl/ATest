package com.shirley.aTest.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月8日 下午3:07:09
 */
public class Request implements Comparable<Request>,Serializable{
	private static final long serialVersionUID = 1L;
	private String url;
	private String api;
	private int caseId;
	private String method;
	private Map<String, String> headers;
	private Map<String, String> paramMap;
	private String paramStr;
	private List<Asserts> asserts;
	private int priority;
	private int timeout;
	private int redirect;
	private int retry;
	private int interval;
	private int delay;
	private Map<String, String> bindVariables;
	private Map<String, String> variables;
	private String caseVariableSplit;
	private Map<String, String> caseVariables;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
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

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	
	public String getCaseVariableSplit() {
		return caseVariableSplit;
	}

	public void setCaseVariableSplit(String caseVariableSplit) {
		this.caseVariableSplit = caseVariableSplit;
	}

	public Map<String, String> getCaseVariables() {
		return caseVariables;
	}

	public void setCaseVariables(Map<String, String> caseVariables) {
		this.caseVariables = caseVariables;
	}

	public void putVariables(Map<String, String> variables) {
		this.variables.putAll(variables);
	}
	
	@Override
	public int compareTo(Request request) {
		// TODO Auto-generated method stub
		return this.getPriority() - request.getPriority();
	}

	@Override
	public String toString() {
		StringBuffer assertStr = new StringBuffer();
		if (asserts != null) {
			for (Asserts assertObject : asserts) {
				assertStr.append(assertObject.toString());
			}
		}

		return "地址：" + url + api + "\n" + "请求方法：" + method + "\n" + "请求头："
				+ (null == headers || headers.size() < 1 ? "" : headers.toString()) + "\n" + "请求参数（map）："
				+ (null == paramMap || paramMap.size() < 1 ? "" : paramMap.toString()) + "\n" + "请求参数（raw）：" + paramStr
				+ "\n" + "断言：" + assertStr.toString() + "\n" + "优先级：" + priority + "\n" + "请求超时：" + timeout + "\n"
				+ "retry=" + retry + "\n" + "delay=" + delay + "\n" + "用例变量："
				+ (null == variables || variables.size() < 1 ? "" : variables.toString()) + "\n" + "绑定测试集变量："
				+ (bindVariables == null || bindVariables.size() < 1 ? "" : bindVariables.toString());
	}

}
