package com.shirley.aTest.entity;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月8日 下午2:56:48
 */
public class AssertResult {
	private int id;
	private int taskId;
	private int caseId;
	private String url;
	private String requestContent;
	private String responseContent;
	private String assertResult;
	private String status;
	private String createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAssertResult() {
		return assertResult;
	}

	public void setAssertResult(String assertResult) {
		this.assertResult = assertResult;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
