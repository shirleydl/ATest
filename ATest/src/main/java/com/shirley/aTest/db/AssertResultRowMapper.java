package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.shirley.aTest.entity.AssertResult;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年7月11日 上午10:28:11
 */
public class AssertResultRowMapper implements RowMapper<AssertResult> {

	@Override
	public AssertResult mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int id = rs.getInt("id");
		int taskId = rs.getInt("task_id");
		String url = rs.getString("url");
		String requestContent = rs.getString("requestContent");
		String responseContent = rs.getString("responseContent");
		String assertResult = rs.getString("assertresult");
		Timestamp createTime = rs.getTimestamp("createtime");
		// 把数据封装对象
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		AssertResult assertResultObjest = new AssertResult();
		assertResultObjest.setId(id);
		assertResultObjest.setTaskId(taskId);
		assertResultObjest.setUrl(url);
		assertResultObjest.setRequestContent(requestContent);
		assertResultObjest.setResponseContent(responseContent);
		assertResultObjest.setAssertResult(assertResult);
		assertResultObjest.setCreateTime(df.format(createTime));
		return assertResultObjest;
	}

}
