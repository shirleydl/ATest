package com.shirley.aTest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.EmailRowMapper;
import com.shirley.aTest.entity.Email;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月5日 上午8:58:12
 */
@Repository("emailDAO")
public class EmailDAO implements IEmailDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Email> QueryEmail(int currentPageNo, int pageSize, int id, String name, String from, String receives) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from email where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != from && !"".equals(from)) {
			sql.append(" and from_email like ?");
			queryList.add("%" + from + "%");
		}
		if (null != receives && !"".equals(receives)) {
			sql.append(" and receives_email like ?");
			queryList.add("%" + receives + "%");
		}
		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Email> emailLists = new ArrayList<Email>();
		for (Map<String, Object> row : list) {
			Email email = new Email();
			email.setId((Integer) row.get("id"));
			email.setName((String) row.get("name"));
			email.setFrom((String) row.get("from_email"));
			email.setHost((String) row.get("host"));
			email.setReceives((String) row.get("receives_email"));
			emailLists.add(email);
		}
		return emailLists;
	}

	@Override
	public int QueryEmailCount(int id, String name, String from, String receives) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select count(id) from email where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and name like ?");
			queryList.add("%" + name + "%");
		}
		if (null != from && !"".equals(from)) {
			sql.append(" and from_email like ?");
			queryList.add("%" + from + "%");
		}
		if (null != receives && !"".equals(receives)) {
			sql.append(" and receives_email like ?");
			queryList.add("%" + receives + "%");
		}
		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Boolean AddEmail(Email email) {
		// TODO Auto-generated method stub
		String sql = "insert into email (name,from_email,pass,host,receives_email) values (?,?,?,?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { email.getName(), email.getFrom(), email.getPass(),
				email.getHost(), email.getReceives() });
		return row > 0;
	}

	@Override
	public Boolean DeleteEmails(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from email where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateEmail(Email email) {
		// TODO Auto-generated method stub
		String sql = "update email set name=?,from_email = ?,pass=?,host=?,receives_email=? where id = ?";
		Object args[] = new Object[] { email.getName(), email.getFrom(), email.getPass(), email.getHost(),
				email.getReceives(), email.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Email QueryEmailById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,from_email,pass,host,receives_email from email where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new EmailRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

}
