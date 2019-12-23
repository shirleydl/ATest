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
		String sql = "select id,name,beforeTask_id,replaceInfo_id,email_id,isSend,isFailSend from task where startTime < unix_timestamp(now())*1000 and status=1 and isLoop=0";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		List<DoTaskId> doTaskIds = new ArrayList<DoTaskId>();
		for (Map<String, Object> row : list) {
			DoTaskId doTaskId = new DoTaskId();
			doTaskId.setId((Integer) row.get("id"));
			doTaskId.setName((String) row.get("name"));
			doTaskId.setBeforeTaskId((Integer) row.get("beforeTask_id"));
			doTaskId.setReplaceInfoId((Integer) row.get("replaceInfo_id"));
			doTaskId.setIsSend((Integer) row.get("isSend"));
			doTaskId.setEmailId((Integer) row.get("email_id"));
			doTaskId.setIsFailSend((Integer) row.get("isFailSend"));
			doTaskIds.add(doTaskId);
		}
		return doTaskIds;
	}
	
	public List<DoTaskId> QueryByTaskName(String taskName) {
				String sql = "select id,name,beforeTask_id,replaceInfo_id,email_id,isSend,isFailSend from task where isLoop=1 and name like ? and status in(1,3)";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql,taskName);
		List<DoTaskId> doTaskIds = new ArrayList<DoTaskId>();
		for (Map<String, Object> row : list) {
			DoTaskId doTaskId = new DoTaskId();
			doTaskId.setId((Integer) row.get("id"));
			doTaskId.setName((String) row.get("name"));
			doTaskId.setBeforeTaskId((Integer) row.get("beforeTask_id"));
			doTaskId.setReplaceInfoId((Integer) row.get("replaceInfo_id"));
			doTaskId.setIsSend((Integer) row.get("isSend"));
			doTaskId.setEmailId((Integer) row.get("email_id"));
			doTaskId.setIsFailSend((Integer) row.get("isFailSend"));
			doTaskIds.add(doTaskId);
		}
		return doTaskIds;
	}

	public Boolean UpdateTaskStatus(int id, int status) {
		String sql = "update task set status=? where id = ? and status=1";
		Object args[] = new Object[] { status, id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}
	
	public Boolean UpdateTaskStatusFromName(int id, int status) {
		String sql = "update task set status=? where id = ? and status in(1,3)";
		Object args[] = new Object[] { status, id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}
	
	public Boolean UpdateFinishTaskStatus(int id, int status) {
		String sql = "update task set status=? where id = ? and status=2";
		Object args[] = new Object[] { status, id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}
	
}
