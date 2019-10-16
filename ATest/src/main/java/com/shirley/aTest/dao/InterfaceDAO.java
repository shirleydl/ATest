package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.InterfaceRowMapper;
import com.shirley.aTest.entity.Interface;

/**
 * @Description: TODO(接口对象DAO)
 * @author 371683941@qq.com
 * @date 2019年6月20日 下午1:58:02
 */
@Repository("interfaceDAO")
public class InterfaceDAO implements IInterfaceDAO {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Interface> QueryInterface(int currentPageNo, int pageSize, String name, String api,
			String environmentName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select interface.id,interface.name,interface.api,environment.name as environmentName from interface left join environment on interface.environment_id=environment.id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != environmentName && !"".equals(environmentName)) {
			sql.append(" and environment.name like ?");
			queryList.add("%" + environmentName + "%");
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and interface.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != api && !"".equals(api)) {
			sql.append(" and interface.api like ?");
			queryList.add("%" + api + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Interface> interfaceLists = new ArrayList<Interface>();
		for (Map<String, Object> row : list) {
			Interface interfaceObject = new Interface();
			interfaceObject.setId((Integer) row.get("id"));
			interfaceObject.setName((String) row.get("name"));
			interfaceObject.setApi((String) row.get("api"));
			interfaceObject.setEnvironmentName((String) row.get("environmentName"));
			interfaceLists.add(interfaceObject);
		}
		return interfaceLists;
	}

	@Override
	public int QueryInterfaceCount(String name, String api, String environmentName) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select count(interface.id) from interface left join environment on interface.environment_id=environment.id where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != environmentName && !"".equals(environmentName)) {
			sql.append(" and environment.name like ?");
			queryList.add("%" + environmentName + "%");
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and interface.name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != api && !"".equals(api)) {
			sql.append(" and interface.api like ?");
			queryList.add("%" + api + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Boolean AddInterface(Interface interfaceObject) {
		// TODO Auto-generated method stub
		String sql = "insert into interface (name,environment_id,api,description) values (?,?,?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { interfaceObject.getName(),
				interfaceObject.getEnvironmentId(), interfaceObject.getApi(), interfaceObject.getDescription() });
		return row > 0;
	}

	@Override
	public Boolean DeleteInterfaces(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from interface where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}
	
	@Override
	public Boolean FindInterfaceEnvironment(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from interface where environment_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.queryForObject(sql, paramMap, Integer.class);
		return row < 1;
	}

	@Override
	public Boolean UpdateInterface(Interface interfaceObject) {
		// TODO Auto-generated method stub
		String sql = "update interface set name = ?,environment_id=?,api=?,description=? where id = ?";
		Object args[] = new Object[] { interfaceObject.getName(), interfaceObject.getEnvironmentId(),
				interfaceObject.getApi(), interfaceObject.getDescription(), interfaceObject.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Interface QueryInterfaceById(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from interface left join environment on interface.environment_id=environment.id where interface.id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new InterfaceRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Interface> QueryInterfaces(int currentPageNo, int pageSize, int id, String name, String api) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select id,name,api from interface where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != api && !"".equals(api)) {
			sql.append(" and api like ?");
			queryList.add("%" + api + "%");
		}
		if (0 != currentPageNo && 0 != pageSize) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}

		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Interface> interfaceLists = new ArrayList<Interface>();
		for (Map<String, Object> row : list) {
			Interface interfaceObject = new Interface();
			interfaceObject.setId((Integer) row.get("id"));
			interfaceObject.setName((String) row.get("name"));
			interfaceObject.setApi((String) row.get("api"));
			interfaceLists.add(interfaceObject);
		}

		return interfaceLists;
	}

}
