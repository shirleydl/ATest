package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.Environment;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.EnvironmentService;

/**
 * @Description: TODO(环境控制类)
 * @author 371683941@qq.com
 * @date 2019年6月15日 下午9:24:25
 */
@Controller
public class EnvironmentController {
	@Resource(name = "environmentService")
	private EnvironmentService environmentService;

	@RequestMapping(value = "/environmentList", method = RequestMethod.GET)
	public String environmentList() {
		return "environmentList";
	}

	@RequestMapping(value = "/addEnvironment", method = RequestMethod.GET)
	public String addEnvironment() {
		return "addEnvironment";
	}

	@RequestMapping(value = "/queryEnvironment", method = RequestMethod.GET)
	public String queryEnvironment() {
		return "environmentDetail";
	}

	/**
	 * 查找环境集（by 环境名/环境地址）
	 */
	@RequestMapping(value = "/queryEnvironments", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<Environment> queryEnvironments(Integer pageNumber, Integer pageSize, String name, String url) {
		List<Environment> environments = environmentService.QueryEnvironment((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), name, url);
		PageHelper<Environment> pageHelper = new PageHelper<Environment>();
		// 统计总记录数
		pageHelper.setTotal(environmentService.QueryEnvironmentCount(name, url));
		pageHelper.setRows(environments);
		return pageHelper;
	}

	/**
	 * 输入框自动查找环境集（by 环境名）
	 */
	@RequestMapping(value = "/queryEnvironments", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<Environment> queryEnvironments(String keyword) {
		List<Environment> environments = environmentService.QueryEnvironment(0, 0, keyword, null);
		BigAutocompleteDataHelper<Environment> jsonHelper = new BigAutocompleteDataHelper<Environment>();
		jsonHelper.setData(environments);
		return jsonHelper;
	}

	/**
	 * 新增环境
	 */
	@RequestMapping(value = "/toAddEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddEnvironment(String name, String url) {
		if (null != name && null != url && !"".equals(name) && !"".equals(url)) {
			Environment environment = new Environment();
			environment.setName(name);
			environment.setUrl(url);
			return environmentService.AddEnvironment(environment);
		}
		return false;
	}

	/**
	 * 删除环境集（by 环境id集）
	 */
	@RequestMapping(value = "/toDelEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelEnvironment(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return environmentService.DeleteEnvironments(idList);
	}

	@RequestMapping(value = "/toQueryEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public Environment toQueryEnvironment(Integer id) {
		if (null != id)
			return environmentService.QueryEnvironmentById(id);
		return null;
	}

	/**
	 * 更新环境字段：环境名、环境地址（by 环境id）
	 */
	@RequestMapping(value = "/updateEnvironment", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updateEnvironment(Integer id, String name, String url) {
		if (null != name && null != id && null != url && !"".equals(name) && !"".equals(url)) {
			Environment environment = new Environment();
			environment.setId(id);
			environment.setName(name);
			environment.setUrl(url);
			return environmentService.UpdateEnvironment(environment);
		}
		return false;
	}

}
