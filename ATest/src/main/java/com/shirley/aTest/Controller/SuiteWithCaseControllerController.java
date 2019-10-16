package com.shirley.aTest.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shirley.aTest.entity.SuiteWithCaseController;
import com.shirley.aTest.service.SuiteWithCaseControllerService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 371683941@qq.com
 * @date 2019年9月9日 下午3:01:55
 */

@Controller
public class SuiteWithCaseControllerController {
	@Resource(name = "suiteWithCaseControllerService")
	private SuiteWithCaseControllerService suiteWithCaseControllerService;

	@RequestMapping(value = "/controllerDetail", method = RequestMethod.GET)
	public String controllerDetail() {
		return "controllerDetail";
	}

	@RequestMapping(value = "/queryAsserts", method = RequestMethod.GET)
	public String queryAsserts() {
		return "controllerAsserts";
	}

	@RequestMapping(value = "/toQuerySuiteWithCaseControllerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toQuerySuiteWithCaseControllerList(Integer testSuiteId) {
		if (null != testSuiteId) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SuiteWithCaseController> suiteWithCaseControllers = suiteWithCaseControllerService
					.QueryControllerByTestSuiteId(testSuiteId);
			Collections.sort(suiteWithCaseControllers);
			map.put("suiteWithCaseControllers", suiteWithCaseControllers);
			return map;
		}
		return null;
	}

	@RequestMapping(value = "/toQuerysuiteWithCaseControllerList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> toQuerysuiteWithCaseControllerList(Integer testSuiteId) {
		if (null != testSuiteId) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SuiteWithCaseController> suiteWithCaseControllers = suiteWithCaseControllerService
					.QueryControllerByTestSuiteId(testSuiteId);
			Collections.sort(suiteWithCaseControllers);
			map.put("suiteWithCaseControllers", suiteWithCaseControllers);
			return map;
		}
		return null;
	}

	@RequestMapping(value = "/toAddSuiteWithCaseController", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddSuiteWithCaseController(Integer testSuiteId, String controllerType, Integer startPriority,
			Integer priority, Integer endPriority, Integer loopCount) {
		if (null != testSuiteId && null != controllerType && null != startPriority && null != endPriority
				&& startPriority <= endPriority) {
			SuiteWithCaseController suiteWithCaseController = new SuiteWithCaseController();
			suiteWithCaseController.setTestSuiteId(testSuiteId);
			suiteWithCaseController.setControllerType(controllerType);
			suiteWithCaseController.setStartPriority(startPriority);
			suiteWithCaseController.setPriority(null == priority ? 0 : priority);
			suiteWithCaseController.setEndPriority(endPriority);
			suiteWithCaseController.setLoopCount(null == loopCount ? 0 : loopCount);
			return suiteWithCaseControllerService.AddSuiteWithCaseController(suiteWithCaseController);
		}
		return false;
	}

	@RequestMapping(value = "/toUpdateSuiteWithCaseController", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateSuiteWithCaseController(Integer id, Integer testSuiteId, String controllerType,
			Integer startPriority, Integer priority, Integer endPriority, Integer loopCount) {
		if (null != id && null != testSuiteId && null != controllerType && null != startPriority
				&& null != endPriority&& startPriority <= endPriority) {
			SuiteWithCaseController suiteWithCaseController = new SuiteWithCaseController();
			suiteWithCaseController.setId(id);
			suiteWithCaseController.setTestSuiteId(testSuiteId);
			suiteWithCaseController.setControllerType(controllerType);
			suiteWithCaseController.setStartPriority(startPriority);
			suiteWithCaseController.setPriority(null == priority ? 0 : priority);
			suiteWithCaseController.setEndPriority(endPriority);
			suiteWithCaseController.setLoopCount(null == loopCount ? 0 : loopCount);
			return suiteWithCaseControllerService.UpdateSuiteWithCaseController(suiteWithCaseController);
		}
		return false;
	}

	@RequestMapping(value = "/toDelSuiteWithCaseController", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelSuiteWithCaseController(Integer id) {
		if (null != id)
			return suiteWithCaseControllerService.DeleteSuiteWithCaseController(id);
		return false;
	}

	@RequestMapping(value = "/toQueryAsserts", method = RequestMethod.POST)
	@ResponseBody
	public String toQueryAssert(Integer id) {
		if (null != id) {
			return suiteWithCaseControllerService.QueryAssertsById(id);
		}
		return null;
	}

	@RequestMapping(value = "/toUpdateAssert", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateAssert(Integer id, String asserts) {
		if (null != id) {
			return suiteWithCaseControllerService.UpdateAsserts(id, asserts);
		}
		return false;

	}

}
