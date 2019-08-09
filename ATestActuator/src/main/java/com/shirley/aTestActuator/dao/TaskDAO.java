package com.shirley.aTestActuator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.shirley.aTestActuator.entity.DoTaskId;

/**
 * @Description: TODO(任务DAO)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:36:26
 */
public class TaskDAO {
	// 获取JdbcTemplate实例
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<DoTaskId> QueryDoTaskId() {
		// TODO Auto-generated method stub
		String sql = "select id,beforeTask_id from task where startTime < unix_timestamp(now())*1000 and status=1";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		List<DoTaskId> doTaskIds = new ArrayList<DoTaskId>();
		for (Map<String, Object> row : list) {
			DoTaskId doTaskId = new DoTaskId();
			doTaskId.setId((Integer) row.get("id"));
			doTaskId.setBeforeTaskId((Integer) row.get("beforeTask_id"));
			doTaskIds.add(doTaskId);
		}
		return doTaskIds;
	}

	public Boolean UpdateTaskStatus(int id, int status) {
		// TODO Auto-generated method stub
		String sql = "update task set status=? where id = ?";
		Object args[] = new Object[] { status, id };
		int row = this.jdbcTemplate.update(sql, args);
		if (row > 0) {
			return true;
		}
		return false;
	}
}
