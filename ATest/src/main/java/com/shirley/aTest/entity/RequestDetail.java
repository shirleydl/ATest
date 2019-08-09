package com.shirley.aTest.entity;

/**
 * @Description: TODO(请求细节对象)
 * @author 371683941@qq.com
 * @date 2019年6月14日 下午1:22:56
 */
public class RequestDetail {
	private int id;
	private String requestStr;
	private String responseStr;
	private String status;
	private Long createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestStr() {
		return requestStr;
	}

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
