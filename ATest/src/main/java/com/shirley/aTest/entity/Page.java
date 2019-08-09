package com.shirley.aTest.entity;

public class Page {
	// 每页显示数量
	private int pageSize;
	// 页码
	private int pageNumber;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}