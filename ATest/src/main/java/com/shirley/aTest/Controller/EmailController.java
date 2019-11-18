package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.Email;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.EmailService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年11月5日 上午10:37:42
 */

@Controller
public class EmailController {
	@Resource(name = "emailService")
	private EmailService emailService;

	@RequestMapping(value = "/emailList", method = RequestMethod.GET)
	public String emailList() {
		return "emailList";
	}

	@RequestMapping(value = "/addEmail", method = RequestMethod.GET)
	public String addEmail() {
		return "addEmail";
	}

	@RequestMapping(value = "/queryEmail", method = RequestMethod.GET)
	public String queryEmail() {
		return "emailDetail";
	}

	/**
	 * 查找email列表（by 发送人/接收人）
	 */
	@RequestMapping(value = "/queryEmails", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Email> queryEmails(Integer pageNumber, Integer pageSize, Integer id, String name, String from,
			String receives) {
		List<Email> emails = emailService.QueryEmail((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name, from, receives);

		PageHelper<Email> pageHelper = new PageHelper<Email>();
		// 统计总记录数
		pageHelper.setTotal(emailService.QueryEmailCount((null == id ? 0 : id), name, from, receives));
		pageHelper.setRows(emails);
		return pageHelper;
	}

	/**
	 * 输入框查找接口用例集（by 用例id）
	 */
	@RequestMapping(value = "/queryEmailById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Email> queryEmailById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
			List<Email> emails = emailService.QueryEmail(0, 0, Integer.parseInt(keyword), null, null, null);
			BigAutocompleteDataHelper<Email> jsonHelper = new BigAutocompleteDataHelper<Email>();
			jsonHelper.setData(emails);
			return jsonHelper;
		}
		return null;
	}

	/**
	 * 输入框自动查找email集（by 收件人）
	 */
	@RequestMapping(value = "/queryEmailsByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Email> queryEmailsByName(String keyword) {
		List<Email> emails = emailService.QueryEmail(0, 0, 0, keyword, null, null);
		BigAutocompleteDataHelper<Email> jsonHelper = new BigAutocompleteDataHelper<Email>();
		jsonHelper.setData(emails);
		return jsonHelper;
	}

	/**
	 * 新增email
	 */
	@RequestMapping(value = "/toAddEmail", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddEmail(String name, String from, String pass, String host, String receives) {
		if (null != name && null != from && null != pass && null != host && !"".equals(name) && !"".equals(from)
				&& !"".equals(pass) && !"".equals(host)) {
			Email email = new Email();
			email.setName(name);
			email.setFrom(from);
			email.setPass(pass);
			email.setHost(host);
			email.setReceives(receives);
			return emailService.AddEmail(email);
		}
		return false;
	}

	/**
	 * 删除email（by email id集）
	 */
	@RequestMapping(value = "/toDelEmail", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelEmail(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return emailService.DeleteEmails(idList);
	}

	@RequestMapping(value = "/toQueryEmail", method = RequestMethod.POST)
	@ResponseBody
	public Email toQueryEmail(Integer id) {
		if (null != id)
			return emailService.QueryEmailById(id);
		return null;
	}

	/**
	 * 更新email字段：email名、发件人、发件人pass、发件人host、收件人（by email id）
	 */
	@RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateEmail(Integer id, String name, String from, String pass, String host, String receives) {
		if (null != id && null != name && null != from && null != pass && null != host && !"".equals(name)
				&& !"".equals(from) && !"".equals(pass) && !"".equals(host)) {
			Email email = new Email();
			email.setId(id);
			email.setName(name);
			email.setFrom(from);
			email.setPass(pass);
			email.setHost(host);
			email.setReceives(receives);
			return emailService.UpdateEmail(email);
		}
		return false;
	}

}
