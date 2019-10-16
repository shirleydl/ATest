package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.ReplaceDAO;
import com.shirley.aTest.dao.TaskDAO;
import com.shirley.aTest.entity.Replace;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年8月14日 上午10:04:20
 */

@Service("replaceService")
public class ReplaceService implements IReplaceService {
	@Resource(name = "replaceDAO")
	private ReplaceDAO replaceDAO;
	@Resource(name = "taskDAO")
	private TaskDAO taskDAO;

	@Override
	public List<Replace> QueryReplaces(int currentPageNo, int pageSize, int id, String name) {
		// TODO Auto-generated method stub
		return replaceDAO.QueryReplaces(currentPageNo, pageSize, id, name);
	}

	@Override
	public int QueryReplaceCount(int id, String name) {
		// TODO Auto-generated method stub
		return replaceDAO.QueryReplaceCount(id, name);
	}

	@Override
	public Replace QueryReplaceById(int id) {
		// TODO Auto-generated method stub
		return replaceDAO.QueryReplaceById(id);
	}

	@Override
	public Boolean AddReplace(Replace replace) {
		// TODO Auto-generated method stub
		return replaceDAO.AddReplace(replace);
	}

	@Override
	public Boolean DeleteReplaces(List<Integer> ids) {
		// TODO Auto-generated method stub
		if (taskDAO.FindTaskRaplace(ids))
			return replaceDAO.DeleteReplaces(ids);
		return false;
	}

	@Override
	public Boolean UpdateReplace(Replace replace) {
		// TODO Auto-generated method stub
		return replaceDAO.UpdateReplace(replace);
	}

}
