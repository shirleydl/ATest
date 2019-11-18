package com.shirley.aTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shirley.aTest.dao.EmailDAO;
import com.shirley.aTest.entity.Email;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月5日 上午10:34:05
 */

@Service("emailService")
public class EmailService implements IEmailService {
	@Resource(name = "emailDAO")
	private EmailDAO emailDAO;

	@Override
	public List<Email> QueryEmail(int currentPageNo, int pageSize, int id, String name, String from, String receives) {
		// TODO Auto-generated method stub
		return emailDAO.QueryEmail(currentPageNo, pageSize, id, name, from, receives);
	}

	@Override
	public int QueryEmailCount(int id, String name, String from, String receives) {
		// TODO Auto-generated method stub
		return emailDAO.QueryEmailCount(id, name, from, receives);
	}

	@Override
	public Boolean AddEmail(Email email) {
		// TODO Auto-generated method stub
		return emailDAO.AddEmail(email);
	}

	@Override
	public Boolean DeleteEmails(List<Integer> ids) {
		// TODO Auto-generated method stub
		return emailDAO.DeleteEmails(ids);
	}

	@Override
	public Boolean UpdateEmail(Email email) {
		// TODO Auto-generated method stub
		return emailDAO.UpdateEmail(email);
	}

	@Override
	public Email QueryEmailById(int id) {
		// TODO Auto-generated method stub
		return emailDAO.QueryEmailById(id);
	}

}
