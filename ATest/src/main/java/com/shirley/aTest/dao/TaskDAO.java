package com.shirley.aTest.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.shirley.aTest.db.TaskRowMapper;
import com.shirley.aTest.entity.Task;

/**
 * @Description: TODO(任务DAO)
 * @author 371683941@qq.com
 * @date 2019年6月21日 下午10:36:26
 */
@Repository("taskDAO")
public class TaskDAO implements ITaskDAO {
	// 获取JdbcTemplate实例
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcN;

	@Override
	public List<Task> QueryTask(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select id,name,beforeTask_id,replaceInfo_id,status,isLoop,isSend,email_id,isFailSend,starttime,updatetime from task where 1=1");
		List<Object> queryList = new ArrayList<Object>();

		if (0 != id) {
			sql.append(" and task.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and task.name like ?");
			queryList.add("%" + name + "%");
		}

		if (currentPageNo != 0 && pageSize != 0) {
			sql.append(" limit ?,?");
			queryList.add((currentPageNo - 1) * pageSize);
			queryList.add(pageSize);
		}
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString(), queryList.toArray());
		List<Task> testSuiteLists = new ArrayList<Task>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> row : list) {
			Task task = new Task();
			task.setId((Integer) row.get("id"));
			task.setName((String) row.get("name"));
			task.setBeforeTaskId((Integer) row.get("beforeTask_id"));
			task.setReplaceInfoId((Integer) row.get("replaceInfo_id"));
			task.setStatus((Integer) row.get("status"));
			task.setIsLoop((Integer) row.get("isLoop"));
			task.setEmailId((Integer) row.get("email_id"));
			task.setIsSend((Integer) row.get("isSend"));
			task.setIsFailSend((Integer) row.get("isFailSend"));
			task.setStartTime((Long) row.get("starttime"));
			task.setUpdateTime(df.format((Timestamp) row.get("updatetime")));
			testSuiteLists.add(task);
		}
		return testSuiteLists;
	}

	@Override
	public int QueryTaskCount(int id, String name) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select count(id) from task where 1=1");
		List<Object> queryList = new ArrayList<Object>();
		if (0 != id) {
			sql.append(" and task.id = ?");
			queryList.add(id);
		}
		if (null != name && !"".equals(name)) {
			sql.append(" and task.name like ?");
			queryList.add("%" + name + "%");
		}

		return this.jdbcTemplate.queryForObject(sql.toString(), queryList.toArray(), Integer.class);
	}

	@Override
	public Task QueryTaskById(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from task where id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Boolean FindTaskRaplace(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from task where replaceInfo_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.queryForObject(sql, paramMap, Integer.class);
		return row < 1;
	}

	@Override
	public Boolean FindTaskBeforeTask(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "select count(id) from task where beforeTask_id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.queryForObject(sql, paramMap, Integer.class);
		return row < 1;
	}

	@Override
	public Boolean AddTask(Task task) {
		// TODO Auto-generated method stub
		String sql = "insert into task (name,starttime) values (?,?)";
		int row = this.jdbcTemplate.update(sql, new Object[] { task.getName(), task.getStartTime() });
		return row > 0;
	}

	@Override
	public Boolean DeleteTasks(List<Integer> ids) {
		// TODO Auto-generated method stub
		String sql = "delete from task where id in(:ids)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		int row = this.jdbcN.update(sql, paramMap);
		return row > 0;
	}

	@Override
	public Boolean UpdateTask(Task task) {
		// TODO Auto-generated method stub
		String sql = "update task set name = ?,starttime=? where id = ?";
		Object args[] = new Object[] { task.getName(), task.getStartTime(), task.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateTaskStatus(Task task) {
		// TODO Auto-generated method stub
		String sql = "update task set status=? where id = ?";
		Object args[] = new Object[] { task.getStatus(), task.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateTaskConfig(Task task) {
		// TODO Auto-generated method stub
		String sql = "update task set beforeTask_id=?,replaceInfo_id=?,email_id=?,isFailSend=? where id = ?";
		Object args[] = new Object[] { task.getBeforeTaskId(), task.getReplaceInfoId(), task.getEmailId(),task.getIsFailSend(),
				task.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateTaskStatus(int id, int status) {
		// TODO Auto-generated method stub
		String sql = "update task set status=? where id = ?";
		Object args[] = new Object[] { status, id };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

	@Override
	public Boolean UpdateTaskIsLoop(Task task) {
		// TODO Auto-generated method stub
		String sql = "update task set isLoop=? where id = ?";
		Object args[] = new Object[] { task.getIsLoop(), task.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}
	
	@Override
	public Boolean UpdateTaskIsSend(Task task) {
		// TODO Auto-generated method stub
		String sql = "update task set isSend=? where id = ?";
		Object args[] = new Object[] { task.getIsSend(), task.getId() };
		int row = this.jdbcTemplate.update(sql, args);
		return row > 0;
	}

}
