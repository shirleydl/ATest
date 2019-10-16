package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.EnvironmentRowMapper;
import com.shirley.aTest.entity.Environment;

/**
 * @Description: TODO(环境DAO)
 * @author 371683941@qq.com
 * @date 2019年6月14日 下午5:08:15
 */

@Repository("environmentDAO")
public class EnvironmentDAO implements IEnvironmentDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Environment> QueryEnvironment(int currentPageNo, int pageSize, String name, String url) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from environment where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != url && !"".equals(url)) {
			sql.append(" and url like ?");
			queryList.add("%" + url + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Environment> environmentLists = new ArrayList<Environment>();
		for (Map<String, Object> row : list) {
			Environment environment = new Environment();
			environment.setId((Integer) row.get("id"));
			environment.setName((String) row.get("name"));
			environment.setUrl((String) row.get("url"));
			environmentLists.add(environment);
		}
		return environmentLists;
	}

	@Override
	public Boolean AddEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		String sql = "insert into environment (name,url) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { environment.getName(), environment.getUrl() });
		return row > 0;
	}

	@Override
	public Boolean DeleteEnvironments(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from environment where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		String sql = "update environment set name = ?,url=? where id = ?";
		Object args[] = new Object[] { environment.getName(), environment.getUrl(), environment.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Environment QueryEnvironmentById(int id) {
		// TODO Auto-generated method stub
		String sql = "select name,url from environment where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new EnvironmentRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int QueryEnvironmentCount(String name, String url) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select count(id) from environment where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != url && !"".equals(url)) {
			sql.append(" and url like ?");
			queryList.add("%" + url + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

}
