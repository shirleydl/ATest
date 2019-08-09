package com.shirley.aTest.entity;

/**
 * @Description: TODO(测试集对象)
 * @author 371683941@qq.com
 * @date 2019年6月14日 上午9:54:48
 */
public class TestSuite {
	private int id;
	private String name;
	private String description;
	private int createrId;
	private int status;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
