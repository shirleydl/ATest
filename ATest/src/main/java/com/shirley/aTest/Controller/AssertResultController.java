package com.shirley.aTest.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.AssertResult;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.service.AssertResultService;

/**
 * @Description: TODO(测试报告控制类)
 * @author 371683941@qq.com
 * @date 2019年7月11日 上午10:56:43
 */
@Controller
public class AssertResultController {

	@Resource(name = "assertResultService")
	private AssertResultService assertResultService;

	@RequestMapping(value = "/assertResultList", method = RequestMethod.GET)
	public String assertResultList() {
		return "assertResult";
	}

	@RequestMapping(value = "/assertResultDetail", method = RequestMethod.GET)
	public String assertResultDetail() {
		return "assertResultDetail";
	}

	/**
	 * 查找断言结果集（by 任务id）
	 */
	@RequestMapping(value = "/queryAssertResults", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<AssertResult> queryAssertResults(Integer pageNumber, Integer pageSize, Integer taskId) {
		if (null != pageNumber && null != pageSize && null != taskId) {
			List<AssertResult> assertResults = assertResultService.QueryAsserts(pageNumber, pageSize, taskId);
			PageHelper<AssertResult> pageHelper = new PageHelper<AssertResult>();
			// 统计总记录数
			pageHelper.setTotal(assertResultService.QueryAssertsCount(taskId));
			pageHelper.setRows(assertResults);
			return pageHelper;
		}
		return null;
	}

	/**
	 * 查找断言结果详情
	 */
	@RequestMapping(value = "/queryAssertResult", method = RequestMethod.POST)
	@ResponseBody
	public AssertResult queryAssertResult(Integer assertResultId) {
		if (null != assertResultId)
			return assertResultService.QueryAssert(assertResultId);
		return null;
	}
}
