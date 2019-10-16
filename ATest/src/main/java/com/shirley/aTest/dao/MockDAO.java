package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.shirley.aTest.db.MockRowMapper;
import com.shirley.aTest.entity.Mock;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月4日 下午2:08:16
 */

@Repository("mockDAO")
public class MockDAO implements IMockDAO {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Mock> QueryMocks(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select id,name from mock where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (0 != currentPageNo && 0 != pageSize) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}

		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Mock> mockLists = new ArrayList<Mock>();
		for (Map<String, Object> row : list) {
			Mock mock = new Mock();
			mock.setId((Integer) row.get("id"));
			mock.setName((String) row.get("name"));
			mockLists.add(mock);
		}
		return mockLists;
	}

	@Override
	public int QueryMockCount(int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select count(id) from mock where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Mock QueryMockById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,bindvariable_mocks from mock where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new MockRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean AddMock(Mock mock) {
		// TODO Auto-generated method stub
		String sql = "insert into mock (name,bindvariable_mocks) values (?,?)";
		Gson gson = new Gson();
		int row = this.jdbcTemplate.update(sql,
				new Object[] { mock.getName(), gson.toJson(mock.getBindVariableMocks()) });
		return row > 0;
	}

	@Override
	public Boolean DeleteMocks(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from mock where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateMock(Mock mock) {
		// TODO Auto-generated method stub
		String sql = "update mock set name=?,bindvariable_mocks=? where id = ?";
		Gson gson = new Gson();
		Object args[] = new Object[] { mock.getName(), gson.toJson(mock.getBindVariableMocks()), mock.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
