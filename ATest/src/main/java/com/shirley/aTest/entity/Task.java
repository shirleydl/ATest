package com.shirley.aTest.entity;

/**
 * @Description: TODO(任务对象)
 * @author 371683941@qq.com
 * @date 2019年6月14日 下午1:01:08
 */
public class Task {
	private int id;
	private String name;
	private int beforeTaskId;
	private int isLoop;
	private Long startTime;
	private int replaceInfoId;
	private int createrId;
	private int status;
	private String createTime;
	private String updateTime;

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

	public int getBeforeTaskId() {
		return beforeTaskId;
	}

	public void setBeforeTaskId(int beforeTaskId) {
		this.beforeTaskId = beforeTaskId;
	}

	public int getIsLoop() {
		return isLoop;
	}

	public void setIsLoop(int isLoop) {
		this.isLoop = isLoop;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public int getReplaceInfoId() {
		return replaceInfoId;
	}

	public void setReplaceInfoId(int replaceInfoId) {
		this.replaceInfoId = replaceInfoId;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
