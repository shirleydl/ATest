package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.Task;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月4日 下午2:59:26
 */
public class TaskRowMapper implements RowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		// 获取结果集中的数据
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int status = rs.getInt("status");
		int isSend = rs.getInt("isSend");
		int emailId = rs.getInt("email_id");
		Long startTime = rs.getLong("starttime");
		Timestamp updateTime = rs.getTimestamp("updatetime");
		// 把数据封装对象
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Task task = new Task();
		task.setId(id);
		task.setName(name);
		task.setStatus(status);
		task.setEmailId(emailId);
		task.setStartTime(startTime);
		task.setUpdateTime(df.format(updateTime));
		return task;
	}
}
