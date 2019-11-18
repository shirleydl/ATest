package com.shirley.aTest.entity;
/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年11月4日 下午5:45:24
*/
public class Email {
	private int id;
	private String name;
	private String from;
	private String pass;
	private String host;
	private String receives;
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getReceives() {
		return receives;
	}
	public void setReceives(String receives) {
		this.receives = receives;
	}

	
}
