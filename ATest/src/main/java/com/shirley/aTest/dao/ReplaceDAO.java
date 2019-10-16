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
import com.shirley.aTest.db.ReplaceRowMapper;
import com.shirley.aTest.entity.Replace;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午9:29:12
 */

@Repository("replaceDAO")
public class ReplaceDAO implements IReplaceDAO {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Replace> QueryReplaces(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select id,name from replaceInfo where 1=1");
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
		List<Replace> replaceLists = new ArrayList<Replace>();
		for (Map<String, Object> row : list) {
			Replace replace = new Replace();
			replace.setId((Integer) row.get("id"));
			replace.setName((String) row.get("name"));
			replaceLists.add(replace);
		}
		return replaceLists;
	}

	@Override
	public int QueryReplaceCount(int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select count(id) from replaceInfo where 1=1");
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
	public Replace QueryReplaceById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,description,replace_url,replace_data,replace_split from replaceInfo where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new ReplaceRowMapper(), id);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Boolean AddReplace(Replace replace) {
		// TODO Auto-generated method stub
		String sql = "insert into replaceInfo (name,description,replace_url,replace_data,replace_split) values (?,?,?,?,?)";
		Gson gson = new Gson();
		int row = this.jdbcTemplate.update(sql, new Object[] { replace.getName(), replace.getDescription(),
				gson.toJson(replace.getReplaceUrl()), gson.toJson(replace.getReplaceData()),replace.getSplit() });
		return row > 0;
	}

	@Override
	public Boolean DeleteReplaces(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from replaceInfo where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateReplace(Replace replace) {
		// TODO Auto-generated method stub
		String sql = "update replaceInfo set name=?,description=?,replace_url = ?,replace_data=?,replace_split=? where id = ?";
		Gson gson = new Gson();
		Object args[] = new Object[] { replace.getName(), replace.getDescription(),
				gson.toJson(replace.getReplaceUrl()), gson.toJson(replace.getReplaceData()), replace.getSplit(),replace.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
