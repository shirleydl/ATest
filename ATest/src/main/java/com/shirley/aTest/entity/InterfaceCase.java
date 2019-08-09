package com.shirley.aTest.entity;

import java.util.Map;

/**
 * @Description: TODO(接口用例对象)
 * @author 371683941@qq.com
 * @date 2019年6月14日 上午9:02:57
 */
public class InterfaceCase {
	private int id;
	private String name;
	private int interfaceId;
	private String interfaceName;
	private String interfaceApi;
	private String description;
	private String method;
	private Map<String, String> headers;
	private String params;
	private String asserts;
	private int createrId;
	private int status;
	private Map<String, String> variables;

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

	public int getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceApi() {
		return interfaceApi;
	}

	public void setInterfaceApi(String interfaceApi) {
		this.interfaceApi = interfaceApi;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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

	public String getAsserts() {
		return asserts;
	}

	public void setAsserts(String asserts) {
		this.asserts = asserts;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

}
