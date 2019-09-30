package com.shirley.aTest.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.shirley.aTest.entity.CaseVariable;

/**
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 371683941@qq.com
* @date 2019年9月10日 下午2:37:53
*/
public class CaseVariableRowMapper implements RowMapper<CaseVariable>  {

	@Override
	public CaseVariable mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		String caseVariablesSplit = rs.getString("case_variables_Split");
		String caseVariables = rs.getString("case_variables");
		// 把数据封装对象
		Gson gson = new Gson();
		Map<String, String> map = new HashMap<String, String>();
		CaseVariable caseVariable = new CaseVariable();
		caseVariable.setCaseVariablesSplit(caseVariablesSplit);
		caseVariable.setCaseVariables((gson.fromJson(caseVariables, map.getClass())));
		return caseVariable;
	}

}
