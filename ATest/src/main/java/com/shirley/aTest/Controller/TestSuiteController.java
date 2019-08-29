package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.TestSuite;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.TestSuiteService;

/**
 * @Description: TODO(测试集控制类)
 * @author 371683941@qq.com
 * @date 2019年6月24日 上午11:58:54
 */
@Controller
public class TestSuiteController {
	@Resource(name = "testSuiteService")
	private TestSuiteService testSuiteService;

	@RequestMapping(value = "/testSuiteList", method = RequestMethod.GET)
	public String testSuiteList() {
		return "testSuiteList";
	}

	@RequestMapping(value = "/addTestSuite", method = RequestMethod.GET)
	public String addTestSuite() {
		return "addTestSuite";
	}

	@RequestMapping(value = "/queryTestSuite", method = RequestMethod.GET)
	public String queryTestSuite() {
		return "testSuiteDetail";
	}

	@RequestMapping(value = "/queryTestSuites", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<TestSuite> queryTestSuites(Integer pageNumber, Integer pageSize, Integer id, String name,
			String testCaseName) {
		List<TestSuite> testSuites = testSuiteService.QueryTestSuite((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name, testCaseName);
		PageHelper<TestSuite> pageHelper = new PageHelper<TestSuite>();
		// 统计总记录数
		pageHelper.setTotal(testSuiteService.QueryTestSuiteCount((null == id ? 0 : id), name, testCaseName));
		pageHelper.setRows(testSuites);
		return pageHelper;
	}

	@RequestMapping(value = "/toAddTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddTestSuite(String name, String description) {
		if (null != name && !"".equals(name)) {
			TestSuite testSuite = new TestSuite();
			testSuite.setName(name);
			testSuite.setDescription(description);
			return testSuiteService.AddTestSuite(testSuite);
		}
		return false;
	}

	@RequestMapping(value = "/toDelTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDeleteTestSuite(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return testSuiteService.DeleteTestSuites(idList);
	}

	@RequestMapping(value = "/toQueryTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public TestSuite toQueryTestSuite(Integer id) {
		if (null != id)
			return testSuiteService.QueryTestSuiteById(id);
		return null;
	}

	@RequestMapping(value = "/toUpdateTestSuite", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateTestSuite(Integer testSuiteId, String name, String description) {
		if (null != name && !"".equals(name)) {
			TestSuite testSuite = new TestSuite();
			testSuite.setId(testSuiteId);
			testSuite.setName(name);
			testSuite.setDescription(description);
			return testSuiteService.UpdateTestSuite(testSuite);
		}
		return false;
	}

	@RequestMapping(value = "/queryTestSuiteById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<TestSuite> queryTestSuiteById(String keyword) {
		if(keyword.matches("[0-9]+")&&Integer.parseInt(keyword)>0){
		List<TestSuite> testSuites = testSuiteService.QueryTestSuite(0, 0, Integer.parseInt(keyword), null, null);
		BigAutocompleteDataHelper<TestSuite> jsonHelper = new BigAutocompleteDataHelper<TestSuite>();
		jsonHelper.setData(testSuites);
		return jsonHelper;
		}
		return null;
	}

	@RequestMapping(value = "/queryTestSuiteByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<TestSuite> queryTestSuiteByName(String keyword) {
		List<TestSuite> testSuites = testSuiteService.QueryTestSuite(0, 0, 0, keyword, null);
		BigAutocompleteDataHelper<TestSuite> jsonHelper = new BigAutocompleteDataHelper<TestSuite>();
		jsonHelper.setData(testSuites);
		return jsonHelper;
	}

}
