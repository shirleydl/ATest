package com.shirley.aTest.jsonHelper;

import java.util.ArrayList;
import java.util.List;

public class BigAutocompleteDataHelper<T> {
	// 实体类集合
	private List<T> data = new ArrayList<T>();

	public BigAutocompleteDataHelper() {
		super();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}