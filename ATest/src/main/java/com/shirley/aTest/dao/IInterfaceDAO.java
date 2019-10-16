package com.shirley.aTest.dao;

import java.util.List;

import com.shirley.aTest.entity.Interface;

/**
 * @Description: TODO(接口对象DAO接口)
 * @author 371683941@qq.com
 * @date 2019年6月20日 下午1:58:50
 */
public interface IInterfaceDAO {
	public List<Interface> QueryInterface(int currentPageNo, int pageSize, String name, String api,
			String environmentName);

	public int QueryInterfaceCount(String name, String api, String environmentName);

	public List<Interface> QueryInterfaces(int currentPageNo, int pageSize, int id, String name, String api);

	public Interface QueryInterfaceById(int id);

	public Boolean AddInterface(Interface interfaceObject);

	public Boolean DeleteInterfaces(List<Integer> ids);

	public Boolean UpdateInterface(Interface interfaceObject);

	public Boolean FindInterfaceEnvironment(List<Integer> ids);
}
