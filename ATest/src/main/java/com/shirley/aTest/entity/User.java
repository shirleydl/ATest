package com.shirley.aTest.entity;

/**
 * @Description: TODO(用户对象)
 * @author 371683941@qq.com
 * @date 2019年6月14日 上午10:00:49
 */
public class User {
	private int id;
	private String userName;
	private String passWord;
	private int role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
