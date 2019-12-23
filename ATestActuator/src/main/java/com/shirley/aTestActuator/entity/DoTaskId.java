package com.shirley.aTestActuator.entity;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月7日 下午4:59:52
 */
public class DoTaskId {
	private int id;
	private String name;
	private int beforeTaskId;
	private int replaceInfoId;
	private int emailId;
	private int isFailSend;
	private int isSend;

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

	public int getReplaceInfoId() {
		return replaceInfoId;
	}

	public void setReplaceInfoId(int replaceInfoId) {
		this.replaceInfoId = replaceInfoId;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public int getIsFailSend() {
		return isFailSend;
	}

	public void setIsFailSend(int isFailSend) {
		this.isFailSend = isFailSend;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	
	
	
	
	
}
