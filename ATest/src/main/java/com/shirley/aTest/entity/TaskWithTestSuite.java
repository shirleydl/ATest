package com.shirley.aTest.entity;

/**
 * @Description: TODO(任务-测试集类)
 * @author 371683941@qq.com
 * @date 2019年7月4日 上午11:47:13
 */
public class TaskWithTestSuite{
	private int id;
	private int taskId;
	private int testSuiteId;
	private String testSuiteName;

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

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

}
