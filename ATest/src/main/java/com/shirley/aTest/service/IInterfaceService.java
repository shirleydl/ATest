package com.shirley.aTest.service;

import java.util.List;

import com.shirley.aTest.entity.Interface;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月20日 下午3:35:45
 */
public interface IInterfaceService {
	public List<Interface> QueryInterface(int currentPageNo, int pageSize, String name, String api,
			String environmentName);

	public int QueryInterfaceCount(String name, String api, String environmentName);

	public List<Interface> QueryInterfaces(int currentPageNo, int pageSize, int id, String name, String api);

	public Interface QueryInterfaceById(int id);

	public Boolean AddInterface(Interface interfaceObject);

	public Boolean DeleteInterfaces(List<Integer> ids);

	public Boolean UpdateInterface(Interface interfaceObject);

}
