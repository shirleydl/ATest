package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.InterfaceDAO;
import com.shirley.aTest.dao.TestCaseDAO;
import com.shirley.aTest.entity.Interface;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年6月20日 下午3:37:31
 */
@Service("interfaceService")
public class InterfaceService implements IInterfaceService {
	@Resource(name = "interfaceDAO")
	private InterfaceDAO interfaceDAO;
	@Resource(name = "testCaseDAO")
	private TestCaseDAO testCaseDAO;

	@Override
	public List<Interface> QueryInterface(int currentPageNo, int pageSize, String name, String api,
			String environmentName) {
		// TODO Auto-generated method stub
		return interfaceDAO.QueryInterface(currentPageNo, pageSize, name, api, environmentName);
	}

	@Override
	public int QueryInterfaceCount(String name, String api, String environmentName) {
		// TODO Auto-generated method stub
		return interfaceDAO.QueryInterfaceCount(name, api, environmentName);
	}

	@Override
	public Boolean AddInterface(Interface interfaceObject) {
		// TODO Auto-generated method stub
		return interfaceDAO.AddInterface(interfaceObject);
	}

	@Override
	public Boolean DeleteInterfaces(List<Integer> ids) {
		// TODO Auto-generated method stub
		if (testCaseDAO.FindCaseInterface(ids))
			return interfaceDAO.DeleteInterfaces(ids);
		return false;
	}

	@Override
	public Boolean UpdateInterface(Interface interfaceObject) {
		// TODO Auto-generated method stub
		return interfaceDAO.UpdateInterface(interfaceObject);
	}

	@Override
	public Interface QueryInterfaceById(int id) {
		// TODO Auto-generated method stub
		return interfaceDAO.QueryInterfaceById(id);
	}

	@Override
	public List<Interface> QueryInterfaces(int currentPageNo, int pageSize, int id, String name, String api) {
		// TODO Auto-generated method stub
		return interfaceDAO.QueryInterfaces(currentPageNo, pageSize, id, name, api);
	}

}
