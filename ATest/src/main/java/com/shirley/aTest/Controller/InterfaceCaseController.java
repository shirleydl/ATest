package com.shirley.aTest.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.shirley.aTest.entity.AssertResult;
import com.shirley.aTest.entity.InterfaceCase;
import com.shirley.aTest.entity.Mock;
import com.shirley.aTest.entity.Request;
import com.shirley.aTest.entity.ResponseContent;
import com.shirley.aTest.jsonHelper.BigAutocompleteDataHelper;
import com.shirley.aTest.jsonHelper.PageHelper;
import com.shirley.aTest.method.DoRequest;
import com.shirley.aTest.service.InterfaceCaseService;
import com.shirley.aTest.service.MockService;

/**
 * @Description: TODO(接口用例控制类)
 * @author 371683941@qq.com
 * @date 2019年6月24日 上午11:58:54
 */
@Controller
public class InterfaceCaseController {
	@Resource(name = "interfaceCaseService")
	private InterfaceCaseService interfaceCaseService;
	@Resource(name = "mockService")
	private MockService mockService;

	@RequestMapping(value = "/interfaceCaseList", method = RequestMethod.GET)
	public String interfaceCaseList() {
		return "interfaceCaseList";
	}

	@RequestMapping(value = "/addInterfaceCase", method = RequestMethod.GET)
	public String addInterfaceCase() {
		return "addInterfaceCase";
	}

	@RequestMapping(value = "/queryInterfaceCase", method = RequestMethod.GET)
	public String queryInterfaceCase() {
		return "interfaceCaseDetail";
	}

	@RequestMapping(value = "/testCaseResultDetail", method = RequestMethod.GET)
	public String testCaseResultDetail() {
		return "testCaseResultDetail";
	}

	@RequestMapping(value = "/copyInterfaceCase", method = RequestMethod.GET)
	public String copyInterfaceCase() {
		return "copyInterfaceCase";
	}

	/**
	 * 查找接口用例集（by 用例id/用例名/接口名/接口api）
	 */
	@RequestMapping(value = "/queryInterfaceCases", method = RequestMethod.GET)
	@ResponseBody
	public PageHelper<InterfaceCase> queryInterfaceCases(Integer pageNumber, Integer pageSize, Integer id, String name,
			String interfaceName, String interfaceApi) {
		List<InterfaceCase> interfaceCases = interfaceCaseService.QueryTestCase((null == pageNumber ? 0 : pageNumber),
				(null == pageSize ? 0 : pageSize), (null == id ? 0 : id), name, interfaceName, interfaceApi);
		PageHelper<InterfaceCase> pageHelper = new PageHelper<InterfaceCase>();
		// 统计总记录数
		pageHelper.setTotal(
				interfaceCaseService.QueryTestCaseCount((null == id ? 0 : id), name, interfaceName, interfaceApi));
		pageHelper.setRows(interfaceCases);
		return pageHelper;
	}

	/**
	 * 输入框查找接口用例集（by 用例名）
	 */
	@RequestMapping(value = "/queryInterfaceCasesByName", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<InterfaceCase> queryInterfaceCasesByName(String keyword) {
		List<InterfaceCase> interfaceCases = interfaceCaseService.QueryTestCase(0, 0, 0, keyword, null, null);
		BigAutocompleteDataHelper<InterfaceCase> jsonHelper = new BigAutocompleteDataHelper<InterfaceCase>();
		jsonHelper.setData(interfaceCases);
		return jsonHelper;
	}

	/**
	 * 输入框查找接口用例集（by 用例id）
	 */
	@RequestMapping(value = "/queryInterfaceCaseById", method = RequestMethod.POST)
	@ResponseBody
	public BigAutocompleteDataHelper<InterfaceCase> queryInterfaceCaseById(String keyword) {
		if (keyword.matches("[0-9]+") && Integer.parseInt(keyword) > 0) {
			List<InterfaceCase> interfaceCases = interfaceCaseService.QueryTestCase(0, 0, Integer.parseInt(keyword),
					null, null, null);
			BigAutocompleteDataHelper<InterfaceCase> jsonHelper = new BigAutocompleteDataHelper<InterfaceCase>();
			jsonHelper.setData(interfaceCases);
			return jsonHelper;
		}
		return null;
	}

	/**
	 * 新增接口用例
	 */
	@RequestMapping(value = "/toAddInterfaceCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toAddInterfaceCase(@RequestParam Map<String, String> interfaceCase) {
		String interfaceIdStr = interfaceCase.get("interfaceId");
		String name = interfaceCase.get("name");
		String method = interfaceCase.get("method");
		if (null != interfaceIdStr && !"".equals(interfaceIdStr) && null != name && !"".equals(name) && null != method
				&& !"".equals(method)) {
			try {
				Map<String, String> headers = new HashMap<String, String>();
				Map<String, String> variables = new LinkedHashMap<String, String>();
				Gson gson = new Gson();
				headers = gson.fromJson(interfaceCase.get("headers"), headers.getClass());
				variables = gson.fromJson(interfaceCase.get("variables"), variables.getClass());
				Integer interfaceId = Integer.parseInt(interfaceIdStr);
				String description = interfaceCase.get("description");
				String params = interfaceCase.get("param");
				String asserts = interfaceCase.get("asserts");
				InterfaceCase interfaceCaseObject = new InterfaceCase();
				interfaceCaseObject.setName(name);
				interfaceCaseObject.setInterfaceId(interfaceId);
				interfaceCaseObject.setDescription(description);
				interfaceCaseObject.setMethod(method);
				interfaceCaseObject.setHeaders(headers);
				interfaceCaseObject.setParams(params);
				interfaceCaseObject.setAsserts(asserts);
				interfaceCaseObject.setVariables(variables);
				return interfaceCaseService.AddTestCase(interfaceCaseObject);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;

	}

	/**
	 * 删除接口用例
	 */
	@RequestMapping(value = "/toDelInterfaceCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toDelInterfaceCase(Integer[] ids) {
		List<Integer> idList = Arrays.asList(ids);
		return interfaceCaseService.DeleteTestCases(idList);
	}

	/**
	 * 查找接口用例详情（by 用例id）
	 */
	@RequestMapping(value = "/toQueryInterfaceCase", method = RequestMethod.POST)
	@ResponseBody
	public InterfaceCase toQueryInterfaceCase(Integer id) {
		if (null != id)
			return interfaceCaseService.QueryTestCaseById(id);
		return null;
	}

	/**
	 * 更新接口用例
	 */
	@RequestMapping(value = "/toUpdateInterfaceCase", method = RequestMethod.POST)
	@ResponseBody
	public Boolean toUpdateInterfaceCase(@RequestParam Map<String, String> interfaceCase) {
		String idStr = interfaceCase.get("id");
		String interfaceIdStr = interfaceCase.get("interfaceId");
		String name = interfaceCase.get("name");
		String method = interfaceCase.get("method");
		if (null != idStr && null != interfaceIdStr && null != name && null != method && !idStr.equals("")
				&& !interfaceIdStr.equals("") && !name.equals("") && !method.equals("")) {
			try {
				Map<String, String> headers = new HashMap<String, String>();
				Map<String, String> variables = new LinkedHashMap<String, String>();
				Gson gson = new Gson();
				headers = gson.fromJson(interfaceCase.get("headers"), headers.getClass());
				variables = gson.fromJson(interfaceCase.get("variables"), variables.getClass());
				Integer id = Integer.parseInt(idStr);
				Integer interfaceId = Integer.parseInt(interfaceIdStr);
				String description = interfaceCase.get("description");
				String params = interfaceCase.get("param");
				String asserts = interfaceCase.get("asserts");
				InterfaceCase interfaceCaseObject = new InterfaceCase();
				interfaceCaseObject.setId(id);
				interfaceCaseObject.setName(name);
				interfaceCaseObject.setInterfaceId(interfaceId);
				interfaceCaseObject.setDescription(description);
				interfaceCaseObject.setMethod(method);
				interfaceCaseObject.setHeaders(headers);
				interfaceCaseObject.setParams(params);
				interfaceCaseObject.setAsserts(asserts);
				interfaceCaseObject.setVariables(variables);
				return interfaceCaseService.UpdateTestCase(interfaceCaseObject);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 执行接口用例并返回断言结果
	 */
	@RequestMapping(value = "/toRequestInterfaceCase", method = RequestMethod.POST)
	@ResponseBody
	public AssertResult toRequestInterfaceCase(Integer interfaceCaseId, Integer mockId) {
		if (null != interfaceCaseId) {
			Request request = new Request();
			request = interfaceCaseService.QueryRequestByTestCaseId(interfaceCaseId);
			Map<String, String> bindMap = new HashMap<String, String>();
			if (null != mockId) {
				Mock mock = mockService.QueryMockById(mockId);
				if (null != mock) {
					bindMap.putAll(mock.getBindVariableMocks());
				}
			}
			DoRequest doRequest = new DoRequest(0, request, bindMap);
			ResponseContent responseContent = new ResponseContent();
			responseContent = doRequest.toRequest();
			doRequest.toUpdateVariables(responseContent);
			return doRequest.toAssert(responseContent);
		}
		return null;
	}

}
